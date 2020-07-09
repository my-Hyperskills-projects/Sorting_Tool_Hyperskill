package sorting;

import java.io.*;

public class Main {
    static String dataType = "word";
    static String sortingType = "natural";
    static InputStream reader = System.in;
    static PrintWriter writer = new PrintWriter(System.out, true);

    public static void main(final String[] args) {
        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {
                case "-dataType":
                    try {
                        dataType = args[i + 1];
                        if (dataType.startsWith("-")) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        i++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No data type defined!");
                    }
                    break;
                case "-sortingType":
                    try {
                        sortingType = args[i + 1];
                        if (sortingType.startsWith("-")) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        i++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No sorting type defined!");
                    }
                    break;
                case "-outputFile":
                    try {
                        writer = new PrintWriter(args[i + 1]);
                        if (args[i + 1].startsWith("-")) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        i++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No output file defined!");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found!");
                    }
                    break;
                case "-inputFile":
                    try {
                        reader = new FileInputStream(args[i + 1]);
                        if (args[i + 1].startsWith("-")) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        i++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No input file defined!");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found!");
                    }
                    break;
                default:
                    System.out.printf("\"%s\" isn't a valid parameter. It's skipped.\n", args[i]);
            }
        }

        CommonTool tool = new CommonTool(dataType, sortingType);
        tool.process(reader, writer);
    }

}
