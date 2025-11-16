package controller;

import models.PrgState;
import models.adts.MyIStack;
import models.adts.MyStack;
import models.exceptions.*;
import models.statements.IStmt;
import repository.IRepository;

public class Controller implements IController{
    private final IRepository repo;

    private boolean displayFlag = false;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
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
            if(displayFlag) {
                System.out.println(prg);
            }
        }
    }

}
