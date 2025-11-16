package controller;

import models.PrgState;
import models.exceptions.*;

public interface IController {
    public PrgState oneStep(PrgState state) throws MyException;
    public void allStep() throws MyException;
}
