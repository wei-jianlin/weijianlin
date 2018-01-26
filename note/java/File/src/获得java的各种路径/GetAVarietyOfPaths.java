package 获得java的各种路径;

public class GetAVarietyOfPaths {
    
    //获得各种路径
    public static void main(String[] args) {
        //bin目录                          /D:/workspace/File/bin/  
        System.out.println(GetAVarietyOfPaths.class.getResource("/").getPath());
        //当前类class文件目录   /D:/workspace/File/bin/getAVarietyOfPaths/  
        System.out.println(GetAVarietyOfPaths.class.getResource("").getPath());
        //工程目录                          D:\workspace\File
        System.out.println(System.getProperty("user.dir")); 
    }
}
