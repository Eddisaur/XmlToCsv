# XmlToCsv

The implementation can be cloned and run with main, and changing the filename within the XmlConverter constructor to the desired xml file that is to be processed.  


### What was done
The current implementation of the processor will successfully collect the required data within the <CSVIntervalData> tags from the xml file and will create csv files for this data.  
Each csv file will have a header containing the row starting with "100", and a trailer containing the row starting with "900". If there are multiple lines in the data starting with "100" or "900", then the implementation will take the row furtherest from the bottom to pick as the header and trailer.  
A csv file will be created for every "200" row there are, with its contents from that row to either the end of the tag, or the next "200" row.  
The tests currently test whether the produced files are correctly in the .csv file format as well whether the files have the same content as the sample test files with the desired content.  
An external library was used in the tests to clean and delete the directory that is created from the implementation, Apache Commons IO.

### What wasn't done
Testing of edge cases such as an empty xml file or data that is not in the required format with the specific row number (etc - 100, 200, 900). The implementation will also take any row of data below the 200 row apart from 100, 200 or 900 rows (not only 300 rows).

### What would be done with more time
More testing could have been done with more sample xml files.  
More optimizations could be done, such as better scanning of the data or using a hashmap for the list of new csv data instead of an arraylist.  
A command line interface could also be implemented for better user experience and more flexibility for choosing files to process as well as the conditions of the processing such as choosing which rows to collect.
