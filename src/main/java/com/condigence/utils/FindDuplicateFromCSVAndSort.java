//package com.condigence.utils;
//
//
//import com.condigence.model.Employee;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class FindDuplicateFromCSVAndSort {
//
//        public static void main(String[] args) throws IOException {
//
//                Set<Employee> employeeSet = new HashSet<>();
//
//                String file = "D:\\condigence\\clients\\brahma-shakti\\src\\main\\java\\com\\condigence\\utils\\employee.csv"; // read file from the below location
//
//                BufferedReader br = null;
//                String line = "";
//
//                System.out.println("Hello!");
//
//
//                // Create an object of file reader
//                // class with CSV file as a parameter.
//                FileReader filereader = new FileReader(file);
//
//                // create csvReader object and skip first Line
//                CSVReader csvReader = new CSVReaderBuilder(filereader)
//                        .withSkipLines(1)
//                        .build();
//                List<String[]> allData = csvReader.readAll();
//
//
//
//
//
//                try {
//
//
//                                System.out.println(line);
//                                String[] details = line.split("");
//
//                                Employee employee = new Employee();
//                                employee.setId(details[0]);
//                                employee.setId(details[1]);
//                                employee.setId(details[2]);
//                                employee.setId(details[3]);
//                                employee.setId(details[4]);
//                                employee.setId(details[5]);
//                                if (employeeSet.add(employee)) {
//                                        log("Processed line: " + employee);
//                                } else if (!isNullOrEmpty(line)) {
//                                        log("--------------- Skipped line: " + employee);
//                                }
//
//
//
//                } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                } finally {
//                        if (br != null) {
//                                try {
//                                        br.close();
//                                } catch (IOException e) {
//                                        e.printStackTrace();
//                                }
//                        }
//                }
//
//
//        }
//
//        // Check if String with spaces is Empty or Null
//        public static boolean isNullOrEmpty(String line) {
//                return line == null || line.trim().isEmpty();
//        }
//
//        // Simple method for system outs
//        private static void log(String s) {
//                System.out.println(s);
//        }
//
//}
