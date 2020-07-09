package sorting;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Класс-помощник, которы совершает действия на основе заданных типов (сортировки и данных)
 */

public class CommonTool {
    String dataType;
    String sortingType;

    public CommonTool(String dataType, String sortingType) {
        this.dataType = dataType;
        this.sortingType = sortingType;
    }

    /**
     * Управляющий метод
     * Опираясь на заданные типы считывает данные и сортирует списки по возрастанию
     * Если тип сортировки natural, то выводит список как есть
     * Иначе с помощью метода  получается карта, которая содержит соответсвующее каждому элементу кол-во,
     * и снова сортируется список(уже в зависимости от частоты)
     */
    public void process(InputStream inputStream, PrintWriter writer) {
        ArrayList<?> data = readData(inputStream);
        sortList(data);

        writer.printf("Total %s: %d.\n", stringForPrint(), data.size());
        if (sortingType.equals("natural")) {
            data.forEach(el -> writer.printf(el + " "));
        } else {
            Map<Object, Integer> map = sortByCount(data);

            Object last = null;
            for (Object o : data) {
                if (o.equals(last)) {
                    continue;
                }

                String desc = map.get(o) + " time(s), " + map.get(o) * 100 / data.size() + "%";
                writer.printf("%s\n", o + ": " + desc);
                last = o;
            }
        }
    }

    private Map<Object, Integer> sortByCount(ArrayList<?> list) {
        Map<Object, Integer> map = new HashMap<>();

        Object current = null;
        int count = 0;
        for (var s : list) {
            if (current == null) {
                current = s;
                count = 1;
            } else if (current.equals(s)) {
                count++;
            } else {
                map.put(current, count);
                current = s;
                count = 1;
            }
        }
        map.put(current, count);

        list.sort(new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                if (map.get(o).equals(map.get(t1))) {
                    return compareElem(o, t1);
                }
                return map.get(o) - map.get(t1);
            }
        });

        return map;
    }

    int compareElem(Object o1, Object o2) {
        if (dataType.equals("long")) {
            return Long.compare((long)o1, (long)o2);
        } else {
            String s1 = (String) o1;
            String s2 = (String) o2;
            return s1.compareTo(s2);
        }
    }


    /**
     * Считывает заданный тип данных (числа, слова, строки)
     * @return список с данными
     */

    public ArrayList<?> readData(InputStream inputStream) {
        switch (dataType) {
            case "long":
                return readNumbers(inputStream);
            case "line":
                return readLines(inputStream);
            default:
                return readWords(inputStream);
        }
    }

    private ArrayList<Long> readNumbers(InputStream inputStream) {
        ArrayList<Long> numList = new ArrayList<>();

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                String elem = scanner.next();
                if (elem.matches("-?\\d+")) {
                    numList.add(Long.parseLong(elem));
                } else {
                    System.out.printf("\"%s\" isn't a long. It's skipped.\n", elem);
                }
            }
        }

        return numList;
    }

    private ArrayList<String> readWords(InputStream inputStream) {
        ArrayList<String> wordList = new ArrayList<>();

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                wordList.add(scanner.next());
            }
        }

        return wordList;
    }

    private ArrayList<String> readLines(InputStream inputStream) {
        ArrayList<String> lineList = new ArrayList<>();

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                lineList.add(scanner.nextLine());
            }
        }

        return lineList;
    }


    /**
     * Сортирует список на основе заданного типа данных, что позволяет быть увереным в
     * выборе способа
     * @param list список данных
     */
    public void sortList(ArrayList list) {
        if (dataType.equals("long")) {
            sortNumbers(list);
        } else {
            sortStrings(list);
        }
    }

    private void sortNumbers(ArrayList<Long> list) {
        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long aLong, Long t1) {
                return Long.compare(aLong, t1);
            }
        });
    }

    private void sortStrings(ArrayList<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if (s.length() == t1.length()) {
                    return s.compareTo(t1);
                } else {
                    return s.length() - t1.length();
                }
            }
        });
    }

    /**
     * Используется, чтобы получить слово для вывода
     * @return слово для вывода
     */
    private String stringForPrint() {
        switch (dataType) {
            case "long":
                return "numbers";
            case "word":
                return "words";
            default:
                return "lines";
        }
    }
}
