# FileService
FileService
允许一次上传多个files。有个requestparam 叫 files 是一个array of MultipartFile。
upload完return的json里有一个urls的array 存上传的attachments的url 这些urls需要存到对应的地方
目前可以直接用s3的url打开file
