package controller;

import models.PrgState;
import models.adts.MyIHeap;
import models.adts.MyIStack;
import models.adts.MyStack;
import models.exceptions.*;
import models.statements.IStmt;
import models.values.Value;
import models.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Controller implements IController{
    private final IRepository repo;

    private boolean displayFlag = false;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        List<Integer> reachable = new ArrayList<>(symTableAddr);
        boolean changed;

        do{
            changed = false;
            List<Integer> newAdddr = heap.entrySet().stream()
                    .filter(e -> reachable.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getAddress())
                    .filter(a -> !reachable.contains(a))
                    .collect(Collectors.toList());

            if(!newAdddr.isEmpty()){
                reachable.addAll(newAdddr);
                changed = true;
            }
        }while(changed);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public PrgState oneStep(PrgState state) throws MyException{
        MyIStack<IStmt> stack = state.getExeStack();
        if(stack.isEmpty()){
            throw new EmptyStack();
        }
        IStmt stmt = stack.pop();
        return stmt.execute(state);
    }

    @Override
    public void allStep() throws MyException{
        PrgState prg = repo.getCurrentProgram();

        repo.logPrgStateExec();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            repo.logPrgStateExec();

            MyIHeap<Value> heap = prg.getHeap();
            List<Integer> symTableAddr = getAddrFromSymTable(prg.getSymTable().getContent().values());
            Map<Integer, Value> newHeapContent = safeGarbageCollector(symTableAddr, heap.getContent());
            heap.setContent(newHeapContent);

            repo.logPrgStateExec();
            if(displayFlag) {
                System.out.println(prg);
            }
        }
    }

}
