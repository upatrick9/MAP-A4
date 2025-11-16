package view;

import controller.Controller;
import models.PrgState;
import models.adts.*;
import models.expressions.*;
import models.statements.*;
import models.types.*;
import models.values.*;
import repository.IRepository;
import repository.Repository;
import view.command.*;

import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {
        IStmt ex1 =
                new CompStmt(new VarDeclStmt("varf", new StringType()),
                        new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                                new CompStmt(new OpenRFile(new VarExp("varf")),
                                        new CompStmt(new VarDeclStmt("varc", new IntType()),
                                                new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                                new CloseRFile(new VarExp("varf"))))))))));

        IStmt ex2 =
                new PrintStmt(new RelExp(">", new ArithExp('+', new ValueExp(new IntValue(2)),
                        new ValueExp(new IntValue(3))),
                        new ValueExp(new IntValue(4))));

        IStmt ex3 = new NopStmt();

        var stk1 = new MyStack<IStmt>();
        var sym1 = new MyDictionary<String, Value>();
        var out1 = new MyList<Value>();
        var ft1  = new FileTable<StringValue, BufferedReader>();
        PrgState prg1 = new PrgState(stk1, sym1, out1, ft1, ex1);

        var stk2 = new MyStack<IStmt>();
        var sym2 = new MyDictionary<String, Value>();
        var out2 = new MyList<Value>();
        var ft2  = new FileTable<StringValue, BufferedReader>();
        PrgState prg2 = new PrgState(stk2, sym2, out2, ft2, ex2);

        var stk3 = new MyStack<IStmt>();
        var sym3 = new MyDictionary<String, Value>();
        var out3 = new MyList<Value>();
        var ft3  = new FileTable<StringValue, BufferedReader>();
        PrgState prg3 = new PrgState(stk3, sym3, out3, ft3, ex3);

        IRepository repo1 = new Repository(prg1, "log1.txt");
        IRepository repo2 = new Repository(prg2, "log2.txt");
        IRepository repo3 = new Repository(prg3, "log3.txt");

        Controller ctr1 = new Controller(repo1);
        Controller ctr2 = new Controller(repo2);
        Controller ctr3 = new Controller(repo3);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new SetDisplayCommand("d", "toggle display (1.On / 2.Off)", ctr1)); // affects ctr1
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.show();
    }
}
