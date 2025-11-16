package models.expressions;

import models.adts.MyIDictionary;
import models.exceptions.MyException;
import models.values.Value;

public class ValueExp implements Exp{
    private final Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public String toString() {
        return e.toString();
    }

}
