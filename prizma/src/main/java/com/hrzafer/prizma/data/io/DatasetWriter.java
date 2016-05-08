package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;

/**
 * abstract class for writing a dataset
 */
public abstract class DatasetWriter {
    public abstract void write(Dataset dataset, String path);
}
