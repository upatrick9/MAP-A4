package models.expressions;

import models.adts.MyIDictionary;
import models.exceptions.*;
import models.values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl) throws MyException;

    Exp deepCopy();
}
