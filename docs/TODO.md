TODO
=======

Features:

1. Defining order of columns in the CSV output
2. Defining types of columns for CSV parsing to map
3. Add support for custom types converters
4. Add converters for some standard types: java.util.Date, BigDecimal etc.
5. ? Support for Array-like output (for better read performance)
6. Lazy read through iterator with tolist option
7. Output Streams support
8. transient keyword support
9. renaming columns (annotations, dictionary map)

Architecture:

1. Separate CSV parsing from output construction
