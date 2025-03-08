package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.InterestsMapper;
import com.example.novel.mapper.LabelMapper;
import com.example.novel.mapper.ReaderMapper;
import com.example.novel.pojo.*;
import com.example.novel.service.MD5Get;
import com.example.novel.service.clientServer;
import com.example.novel.service.mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ReaderController {
    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private InterestsMapper interestsMapper;

    @Autowired
    private LabelMapper labelMapper;
    private static final String UPLOAD_DIR = "D:/web代码/novel/uploads/"; // 确保路径与静态资源目录一致

    @PostMapping("/reader/upload-cover")
    public String uploadCoverImage(@RequestParam("file") MultipartFile file) {
        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // 构造文件保存路径
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            // 保存文件到服务器
            file.transferTo(new File(filePath));
            // 返回可访问的图片路径
            return "http://localhost:8080/uploads/" + fileName; // 返回相对路径
        } catch (IOException e) {
            e.printStackTrace();
            return "文件保存失败：" + e.getMessage();
        }
    }

    @GetMapping("/reader/findAll")
    public List<Reader> find(){
        return readerMapper.selectAllUserAndOrders();
    }
    @GetMapping("/reader/findByPage")
    public IPage getReaderList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Reader> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        IPage ipage = readerMapper.selectPage(page,null);//执行分页查询操作，使用 page 对象和 queryWrapper 对象进行查询，并返回符合条件的用户分页结果。

        return ipage;
    }

    @PostMapping("/reader/create")
    public ResponseEntity<?> createUser(@RequestBody Reader reader) {
        // 在此处进行ID的重复性验证
        int count = readerMapper.existsById(reader.getId());
        if (count >0) {
            // 如果存在重复ID，则返回响应
            String errorMessage = "ID already exists";
            return ResponseEntity.badRequest().body(errorMessage);
        } else {
            // 如果ID不重复，则执行插入操作
            readerMapper.insert(reader);
            return ResponseEntity.ok(reader);
        }
    }

    //登录
    MD5Get md5Get = new MD5Get();
    @PostMapping("/reader/login")
    public ResponseEntity<Reader> findStu(@RequestBody Reader reader) throws Exception {
        // 获取登录表单数据
        String id = reader.getPhone();
        String password = reader.getPassword();
        // 查询数据库获取用户信息
        Reader r = readerMapper.selectByPhone(id);
        // 验证用户名和密码
        System.out.println(r);
        if (r != null && r.getPassword().equals(password)) {
            return ResponseEntity.ok(r); // 返回用户信息
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 返回401错误
        }
    }

    //通过id修改信息
    @PutMapping("/reader/uid/{uid}")
    public Reader updateReader(@PathVariable("uid") int id,@RequestBody Reader reader){
        Reader exist = readerMapper.selectById(id);
        if(exist != null){
            reader.setId(id);
            readerMapper.updateById(reader);
            return reader;
        }else{
            return null;
        }
    }

    //通过id删除信息
    @DeleteMapping("/reader/id/{id}")
    public int delReader(@PathVariable("id") int id){
        return readerMapper.deleteById(id);
    }

    //通过id查询信息
    @GetMapping("/reader/findById/{id}")
    public Reader findBook(@PathVariable("id") int id){
        return readerMapper.selectById(id);
    }

    @PostMapping("/reader/forget")
    public boolean findEmail(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        System.out.println("email"+email);
        //查找stu
        List<Reader> list = readerMapper.selectAllUserAndOrders();
        System.out.println(list.get(0));
        Reader reader = new Reader();
        for(Reader r:list)
        {
            System.out.println(r.getEmail());
            if(r.getEmail().equals(email)&&r.getEmail()!=null)
            {
                reader=r;
            }
        }
//
        if (reader != null) {
            mail ma = new mail();
            clientServer s = new clientServer();
            String message = s.creatID();
            local.MESSAGE.put(email,message);//存在本地类
            ma.sendMail(email, "欢迎使用课程管理系统，这是系统为你生成的验证码：" + message + "。用于修改密码，请勿泄露！", "这是一封来自课程管理系统的验证邮件");
            return true;
        } else {
            return false;
        }
    }
    @PostMapping("/reader/update")
    public boolean changePassword(@RequestBody Map<String, String> loginData) throws Exception {
        boolean a = false;
        String code = loginData.get("code");
        String email = loginData.get("email");
        String newPassword = loginData.get("password");
        System.out.println(local.MESSAGE.get(email));
        System.out.println(email);
        if(code.equals(local.MESSAGE.get(email)))//验证码相同
        {
            Reader r=null;
            List<Reader> list = readerMapper.selectAllUserAndOrders();
            for(Reader su:list)
            {
                if(su.getEmail().equals(email))
                {
                    System.out.println(su.getEmail()+email);
                    r=su;
                    a=true;
                    local.MESSAGE.remove(email);
                }
            }
            if(r!=null)
            {
                System.out.println("r"+r);
                r.setPassword(newPassword);
                readerMapper.updateById(r);//更新
            }
        }
        return a;
    }

    @PostMapping("/reader/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String phone,
                                           @RequestParam String coverImage) {
        try {
            readerMapper.insertReader(username, password, phone, coverImage);
            return ResponseEntity.ok("注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败！");
        }
    }

    @PostMapping("/reader/save")
    public ResponseEntity<Map<String, Object>> saveReaderField(@RequestBody Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        String field = null;
        String value = null;

        // 找到第一个非 username 字段作为要更新的字段
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!entry.getKey().equals("id")) {
                field = entry.getKey();
                value = entry.getValue();
                break;
            }
        }
        if (field == null || value == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Field and value are required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            System.out.println(field);
            System.out.println(value);
            int updatedRows = readerMapper.updateReader(field, value, id);
            if (updatedRows > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Data saved successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Data not updated");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Error saving data: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/reader/search")
    public List<Reader> searchReaders(@RequestParam("keyword") String keyword) {
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", keyword).or().like("address", keyword).or().like("phone",keyword).or().like("email",keyword);
        return readerMapper.selectList(queryWrapper);
    }

}
