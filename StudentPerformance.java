import java.util.*;
import java.io.*;

class Student {
    int roll_no;
    String name;
    List<Integer> marks;
    double average;

    Student(int roll_no, String name, List<Integer> marks) {
        this.roll_no = roll_no;
        this.name = name;
        this.marks = marks;
        calculateAverage();
    }

    void calculateAverage() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        this.average = sum / (double) marks.size();
    }

    @Override
    public String toString() {
        return roll_no + " - " + name + " | Average: " + average;
    }
}

class StudentPerformance {
    public static void main(String[] args) {

        String inputfile = "students.txt";
        String outputfile = "output.txt";

        ArrayList<Student> students = new ArrayList<>();
       //we used hashmap because we can accesss the data of thay using roll_no.we created the hashmap using roll_no, student object
        HashMap<Integer, Student> studentMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputfile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int roll_no = Integer.parseInt(data[0]);
                String name = data[1];

                List<Integer> marks = new ArrayList<>();
                for (int i = 2; i < data.length; i++) {
                    marks.add(Integer.parseInt(data[i]));
                }

                Student student = new Student(roll_no, name, marks);
                students.add(student);
                studentMap.put(roll_no, student);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        students.sort(Comparator.comparingDouble(s -> s.average));

        Student lowest = students.get(0);
        Student highest = students.get(students.size() - 1);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile))) {

            bw.write("Student Analysis Report\n\n");

            for (Student s : students) {
                bw.write(s.toString());
                bw.newLine();
            }

            bw.write("\nTop Performer:\n");
            bw.write(highest.toString());

            bw.write("\n\nLowest Performer:\n");
            bw.write(lowest.toString());

            System.out.println("Analysis completed. Results saved to output.txt");

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
