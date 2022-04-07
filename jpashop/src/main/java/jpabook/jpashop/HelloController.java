package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!!"); // data라는 key에 value로  hello!!!를 넣는다.
        //화면상에 넘겨주는것
        return "hello"; // hello에 .html이 자동으로 붙어서 넘어간다.
    }

}
