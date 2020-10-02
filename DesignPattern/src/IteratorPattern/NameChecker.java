package IteratorPattern;

public class NameChecker {
    MaleStudentNameList maleStudentNameList;
    FemaleStudentNameList femaleStudentNameList;
    public NameChecker(MaleStudentNameList maleList, FemaleStudentNameList femaleList) {
        maleStudentNameList = maleList;
        femaleStudentNameList = femaleList;
    }
    public void printAll() {
        Iterator maleIter = maleStudentNameList.createIterator();
        Iterator femaleIter = femaleStudentNameList.createIterator();
        System.out.println("------------Male Names-------------");
        while (maleIter.hasNext())
            System.out.println((String)maleIter.next());
        System.out.println("---------------Female Names---------------");
        while (femaleIter.hasNext())
            System.out.println((String)femaleIter.next());
    }
}
