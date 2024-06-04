package GRN;

public class Variable {
    private String name;
    private boolean negated;

    public Variable(String name, boolean negated){
        this.name = name;
        this.negated = negated;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }
}
