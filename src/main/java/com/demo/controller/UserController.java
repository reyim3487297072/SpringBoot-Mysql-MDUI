package com.demo.controller;

import com.demo.service.UserService;
import com.demo.util.ResultJson;
import com.demo.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 作者QQ：3487297072
 * 个人项目地址：https://uyclouds.com
 */

@RestController
@Controller
public class UserController {


    @Value("${book.list}") // 获取yml,properties内的值
    String MapUser;

    @Autowired
    UserService userService;

    @RequestMapping("/value")
    public String Value() {
        return MapUser;
    }

    // index
    @RequestMapping("/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView; // 带参数渲染
    }

    // add view
    @RequestMapping("/Add")
    public ModelAndView Add(ModelAndView model) {
        model.setViewName("add");
        model.addObject("data", "添加操作");
        return model;
    }

    // delete view
    @RequestMapping("/Delete")
    public ModelAndView Delete() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("delete");
        modelAndView.addObject("data", "删除操作");
        return modelAndView; // 带参数渲染
        // return "delete"; // 直接渲染
    }

    // modify view
    @RequestMapping("/Modify")
    public ModelAndView Modify(ModelAndView model) {
        model.setViewName("modify");
        model.addObject("data", "修改操作");
        return model;
    }

    // check view
    @RequestMapping("/Check")
    public ModelAndView Check(ModelAndView model) {
        model.setViewName("check");
        model.addObject("data", "查看操作");
        return model;
    }

    // list view
    @RequestMapping("/List")
    public ModelAndView List(ModelAndView model) {
        model.setViewName("list");
        model.addObject("data", "列表操作");
        return model;
    }

    // upload view
    @RequestMapping("/Upload")
    public ModelAndView Upload(ModelAndView modelAndView) {
        modelAndView.setViewName("upload");
        modelAndView.addObject("data", "单个文件上传");
        return modelAndView;
    }

    // uploads view
    @RequestMapping("/Uploads")
    public ModelAndView Uploads(ModelAndView modelAndView) {
        modelAndView.setViewName("uploads");
        modelAndView.addObject("data", "多个文件上传");
        return modelAndView;
    }

    @RequestMapping(value = "/add")
    public ResultJson<String> AddData(String user, String pass, String name, String head, HttpServletRequest httpServletRequest) {
        if ("".equals(user) || "".equals(pass) || "".equals(name) || "".equals(head)) {
            return new ResultJson<>(400, "参数不能为空");
        } else {
            int ret = userService.Add("user", user, pass, name, head, Utils.getIpAddress(httpServletRequest));
            System.err.println("插入执行结果：" + ret);
            if (ret <= 0) {
                return new ResultJson<>(400, "插入失败");
            } else {
                return new ResultJson<>("插入成功");
            }
        }
    }

    @RequestMapping(value = "/delete")
    public ResultJson<String> DeleteData(int id) {
        if ("".equals(String.valueOf(id))) {
            return new ResultJson<>(400, "参数不能为空");
        } else {
            int ret = userService.Delete(id, "user");
            System.err.println("删除执行结果：" + ret);
            if (ret <= 0) {
                return new ResultJson<>(400, "删除失败");
            } else {
                return new ResultJson<>("删除成功");
            }
        }
    }

    @RequestMapping(value = "/modify")
    public ResultJson<String> ModifyData(int id, String user, String pass, String name, String head, HttpServletRequest httpServletRequest) {
        if ("".equals(user) || "".equals(pass) || "".equals(name) || "".equals(head) || "".equals(String.valueOf(id))) {
            return new ResultJson<>(400, "参数不能为空");
        } else {
            int ret = userService.Modify("user", id, user, pass, name, head, Utils.getIpAddress(httpServletRequest));
            System.err.println("修改执行结果：" + ret);
            if (ret <= 0) {
                return new ResultJson<>(400, "修改失败");
            } else {
                return new ResultJson<>("修改成功");
            }
        }
    }

    @RequestMapping(value = "/check")
    public ResultJson<List<Map<String, Object>>> CheckData(int id) {
        if ("".equals(String.valueOf(id))) {
            return new ResultJson<>(400, "参数不能为空");
        } else {
            List<Map<String, Object>> list = userService.Check("user", id);
            System.err.println("查询执行结果：" + list);
            if (list == null || list.size() == 0) {
                return new ResultJson<>(400, "查询失败");
            } else {
                return new ResultJson<>("查询成功", list);
            }
        }
    }

    @RequestMapping(value = "/list")
    public ResultJson<List<Map<String, Object>>> ListData() {
        List<Map<String, Object>> list = userService.List("user");
        System.err.println("列表执行结果：" + list);
        return new ResultJson<>("列表查询成功", list);
    }

    @RequestMapping(value = "/upload")
    public ResultJson<Map<String, Object>> UploadFile(MultipartFile file) throws FileNotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (file != null) {
            String FileType = file.getContentType();
            if ("image/png".equals(FileType) || "image/jpg".equals(FileType) || "image/jpeg".equals(FileType)) {
                map.put("file-name", file.getName()); // 文件名
                map.put("content-type", file.getContentType()); // 文件类型
                map.put("file-size", file.getSize()); // 文件大小

                String FileName = UUID.randomUUID() + "." + Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1); //文件名，包括后缀
                String FilePath = new Utils().getSavePath().concat("\\userHead").concat("\\".concat(Utils.getData())); //存储路径
                File Mfile = new File(FilePath);
                if (!Mfile.exists()) {
                    Mfile.mkdir(); // 文件夹不存在时创建
                }
                File files = new File(FilePath, FileName);
                map.put("file-path", files.getPath());
                try {
                    file.transferTo(files);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ResultJson<>("上传成功", map);
            } else {
                return new ResultJson<>(400, "文件格式不正确");
            }
        } else {
            return new ResultJson<>(400, "文件不能为空");
        }
    }

    @RequestMapping("/uploads")
    public ResultJson<List<String>> UploadsFile(HttpServletRequest request) {
        List<String> list = new ArrayList<String>();
        if (request instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            List<MultipartFile> multipartFiles = multipartHttpServletRequest.getFiles("file");
            // 取出每一个上传文件
            for (MultipartFile file : multipartFiles) {
                try {
                    list.add(this.saveFile(file));        // 保存上传信息
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResultJson<>("上传成功", list);
    }

    public String saveFile(MultipartFile file) throws Exception {
        String path = "空文件";
        if (file != null) { // 有文件上传
            if (file.getSize() > 0) {
                String FileName = UUID.randomUUID() + "." + Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1); //文件名，包括后缀
                String FilePath = new Utils().getSavePath().concat("\\userHead").concat("\\".concat(Utils.getData())); //存储路径
                File Mfile = new File(FilePath);
                if (!Mfile.exists()) {
                    Mfile.mkdir(); // 文件夹不存在时创建
                }
                File saveFile = new File(FilePath, FileName);
                path = saveFile.getPath();
                file.transferTo(saveFile); // 文件保存
            }
        }
        return path;
    }
}


