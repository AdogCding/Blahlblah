package IteratorPattern;

public class IteratorTest {
    public static void main(String[] args) {
        FemaleStudentNameList femaleStudentNameList = new FemaleStudentNameList();
        MaleStudentNameList maleStudentNameList = new MaleStudentNameList();
        NameChecker nameChecker = new NameChecker(maleStudentNameList, femaleStudentNameList);
        nameChecker.printAll();
    }
}
