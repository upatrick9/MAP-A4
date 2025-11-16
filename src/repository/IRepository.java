package repository;

import models.PrgState;
import models.exceptions.*;

public interface IRepository {
    PrgState getCurrentProgram();

    void logPrgStateExec() throws MyException;
}
