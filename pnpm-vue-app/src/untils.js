const express = require("express");
const multer = require("multer");
const path = require("path");
const fs = require("fs");


const app = express();

// 启用 CORS，允许跨域请求
const cors = require("cors");
app.use(cors({
    allowCredentials: true,
    origin: "http://localhost:5173"  // 替换为前端域名
}));

// 配置 multer 用于处理文件上传
const upload = multer({
    dest: "uploads/"  // 上传的文件将存储在项目根目录下的 `uploads` 文件夹中
});

// 创建 uploads 文件夹（如果不存在）
if (!fs.existsSync("uploads")) {
    fs.mkdirSync("uploads");
}

// 图片上传接口
app.post("/book/create", upload.single("file"), (req, res) => {
    const file = req.file;
    if (!file) {
        return res.status(400).json({ error: "No file uploaded" });
    }

    // 获取文件扩展名
    const ext = path.extname(file.originalname);
    const filename = Date.now() + ext;  // 使用时间戳作为文件名

    // 将文件移动到指定目录
    fs.renameSync(file.path, path.join("uploads", filename));

    // 返回图片路径
    const imageUrl = `http://localhost:8080/uploads/${filename}`;
    res.json({ url: imageUrl });
});

// 提供 uploads 文件夹的静态访问
app.use("/uploads", express.static("uploads"));

app.listen(8080, () => {
    console.log("Server is running on http://localhost:8080");
});