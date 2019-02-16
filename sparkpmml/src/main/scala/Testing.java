public class Testing {

    public static void main(String[] args) throws Exception {
        String s = "2018-06-09T07:40:37.387454Z elb123 23.212.50.87:51268 172.31.31.224:80 0.000037 0.493461 0.000028 200 200 0 36615 \"GET http://api.superprod.com:80/ws/metaSearch.json?platform=Cellular&pId=1&searchText=Mtv HTTP/1.1\" \"Dalvik/2.1.0 (Linux; U; Android 6.0; MotoG3 Build/MPI24.65-25)\" - -";
        String s2[] = s.split("GET");
        System.out.println(s2[1].split("HTTP")[0]);




    }
}
