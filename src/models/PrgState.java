package models;

import models.adts.*;
import models.statements.IStmt;
import java.io.BufferedReader;
import models.values.StringValue;
import models.values.Value;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private final MyIFileTable<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;
    private final MyIHeap<Value> heap;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIFileTable<StringValue, BufferedReader> fileTable, IStmt originalProgram) {
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = new MyHeap<>();
        exeStack.push(originalProgram);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Value> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return "Stack: " + exeStack + "\nSymTable: " + symTable + "\nOut: " + out + "\n";
    }
}
