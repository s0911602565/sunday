package com.example.abc.controller;
/*
@Controller     (O)轉HTML 舉例 return "index" 會成功
@RestController (X)轉HTML

@RequestParam
@PathVariable
@RequestBody (json)
@RequestPart 上傳檔案
@RequestHeader 標頭-資訊
@DateTimeFormat 日期格式(搜尋 getMessage9)
@ModelAttribute(沒參數) / (有參數)
* */

import com.example.abc.model.Amt;
import com.example.abc.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("handle")
public class MyController {

    /* http://127.0.0.1:8080/form.html  */
    @RequestMapping
    public String getMessage(@RequestParam(name = "username", required = false, defaultValue = "johnlee") String username, @RequestParam String pwd) {
        return "(handle) " + username + "/" + pwd;
    }

    /* http://127.0.0.1:8080/form.html */
    @RequestMapping("/obj")
    public String getMessage2(User user) {
        return "getMessage2: " + user.getUsername() + " " + user.getPwd();
    }


    //路徑變數 基本型別
    /* http://127.0.0.1:8080/form.html */
    @RequestMapping("/url/{id}/{userName}")
    public String getMessage3(@PathVariable("id") String p_Id, @PathVariable("userName") String p_UserName) {
        return "getMessage3: " + p_UserName + " " + p_Id;
    }

    /*
    開啟 post man ,  http://127.0.0.1:8080/handle/json
    * */
    @RequestMapping("/json")
    public ResponseEntity<User> getJson() {
        User user2 = new User();
        user2.setPwd("123123");
        user2.setUsername("john1ee");
        return new ResponseEntity<User>(user2, HttpStatus.OK);
    }

    /*
    以下貼到post man , 以及使用網址進行取得資料  http://127.0.0.1:8080/handle/json2
    {
    "username":"john",
    "pwd":"123"
    }
    */
    @RequestMapping("/json2")
    public void getJson2(@RequestBody User user) {
        System.out.println("getJson2:" + user.getUsername());
        System.out.println("getJson2:" + user.getPwd());
    }

    /*
    目的:自訂_表頭_資訊
    以下貼到post man  or web,   http://127.0.0.1:8080/handle/json3

    點選 post man 的 Headers 可以看到 key and value
    web模式 > F12 > network > (ctrl + R 如果有提示的話) > 表頭Name 滑鼠右鍵 > 選擇 Response Headers > Manage Headers Column > 增加key(輸入:Content-Type) > 就會出現value了
    */
    @RequestMapping("/json3")
    public HttpEntity<String> getJson3() {
        HttpHeaders r = new HttpHeaders();
        r.set("my", "zzz");
        r.set("whoami", "lee");
        return new HttpEntity<String>("Hello World3", r);
    }

    /*
    目的:自訂_表頭_資訊
    以下貼到post man or web ,   http://127.0.0.1:8080/handle/json4_For_Web

    點選 post man 的 Headers 可以看到 key and value
    web模式 > F12 > network > (ctrl + R 如果有提示的話) > 表頭Name 滑鼠右鍵 > 選擇 Response Headers > Manage Headers Column > 增加key(輸入:Content-Type) > 就會出現value了
    */
    @RequestMapping("/json4_For_Web")
    public void getJson4(HttpServletResponse r) throws Exception {
        r.addHeader("Content-Type", "text/html;charset=UTF-8");
        PrintWriter out = r.getWriter();
        out.write("Hello World4");
        out.close();
    }

    /*
    目的: 建立user類別
    乾淨的
    @ModelAttribute User user 左邊等同 ->  new User()
    http://127.0.0.1:8080/handle/what
    * */
    @RequestMapping("what")
    @ResponseBody
    public String getWhat(@ModelAttribute User user){
        System.out.println("getWhat " +user.getUsername());
        System.out.println("getWhat " +user.getPwd());
        return "getWhat";
    }


    /*
    目的: 建立user類別
    有值
    @ModelAttribute("mUser") User user 左邊等同 ->  m.addAttribute("mUser" , new User())
    http://127.0.0.1:8080/handle/what2
    * */
    @RequestMapping("what2")
    @ResponseBody
    public String getWhat2(@ModelAttribute("mUser")User user , Model m){
        User user2 = (User)m.getAttribute("mUser");
        if(user2 != null){
            System.out.println(user2.getUsername());
            System.out.println(user2.getPwd());
        }

        return "getWhat2";
    }

    /*
    http://127.0.0.1:8080/handle/what3
    * */
    @RequestMapping("what3")
    public String getWhat3(User user){
        System.out.println("what3"+user.getUsername());
        System.out.println("what3"+user.getPwd());
        return "getWhat3";
    }


    //取得-路徑變數 map型態
    @RequestMapping("/url2/{id}/{userName}")
    public String getMessage4(@PathVariable Map pathMap) {/* http://127.0.0.1:8080/form.html */
        System.out.println(pathMap.get("id"));
        System.out.println(pathMap.get("userName"));
        return "getMessage4: " + pathMap;
    }

    //檔案上傳(1個)
    /* http://127.0.0.1:8080/index.html */
    @RequestMapping("/files")
    public String getMessage5(@RequestPart(name = "filez") MultipartFile mf) {
        System.out.println(mf.getOriginalFilename());
        System.out.println(mf.getSize());
        System.out.println();
        return "getMessage5: ";
    }

    //檔案上傳(n個)
    /* http://127.0.0.1:8080/index.html */
    @RequestMapping("/files2")
    public String getMessage6(@RequestPart(name = "filez") List<MultipartFile> mf) {

        for (int i = 0; i < mf.size(); i++) {
            MultipartFile mfs = mf.get(i);
            System.out.println(mfs.getOriginalFilename());
            System.out.println(mfs.getSize());
            System.out.println("----------");
        }
        return "getMessage6: ";
    }

    /*取得(User-Agent)標頭-資訊 Head */
    /* http://127.0.0.1:8080/index2.html */
    @RequestMapping("/head")
    public String getMessage7(@RequestHeader("User-Agent") String ua) {
        System.out.println(ua);
        return "getMessage7:" + ua;
    }

    /*取得(所有)標頭-資訊 Head */
    /* http://127.0.0.1:8080/index2.html */
    @RequestMapping("/head2")
    public String getMessage8(@RequestHeader Map ua) {
        System.out.println(ua);
        return "getMessage8: " + ua;
    }

    /*取得日期 */
    /* http://127.0.0.1:8080/index3.html */
    @RequestMapping("/mydate")
    public String getMessage9(User user, Amt amt) {
        System.out.println(user.getBirthdayA());
        System.out.println(amt.getId() + " " + amt.getMoney());

        return "getMessage9: " + user.getBirthdayA();
    }

}
