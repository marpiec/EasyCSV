package pl.mpieciukiewicz.easycsv.internal;

public interface ResultBuilder<T> {

    void addRowValue(CharSequence value);

    void rowFinished();

    T getResult();
}
