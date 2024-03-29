package io.corrlang.emfintegration.adapters;

public class ReadOnlyException extends RuntimeException {

    public ReadOnlyException() {
        super("Cannot invoke setter or operation because right now only \"read-only\" methods are supported!");
    }
}
