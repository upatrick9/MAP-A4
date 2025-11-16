package view.command;

import controller.Controller;
import java.util.Scanner;

public class SetDisplayCommand extends Command {
    private final Controller controller;
    public SetDisplayCommand(String key, String desc, Controller controller){
        super(key, desc);
        this.controller = controller;
    }
    @Override
    public void execute() {
        System.out.println("Display:\n1. On\n2. Off");
        System.out.print("Choose 1/2: ");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        switch (option) {
            case 1-> {
                controller.setDisplayFlag(true);
                System.out.println("Display is on");
            }
            case 2-> {
                controller.setDisplayFlag(false);
                System.out.println("Display is off");
            }
            default->{
                System.out.println("Invalid option");
                controller.setDisplayFlag(false);
            }
        }
    }
}
