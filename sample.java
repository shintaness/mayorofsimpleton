import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

protected void parse(HttpServletRequest request) throws Exception {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    factory.setSizeThreshold(1024);
    upload.setSizeMax(-1);
    upload.setHeaderEncoding("UTF-8");

    FileItemIterator iter = upload.getItemIterator(request);
    while (iter.hasNext()) {
        FileItemStream item = iter.next();

        if (item.isFormField()) {
            InputStream is = item.openStream();

            trace("key=" + item.getFieldName());
            trace("value=" +  Streams.asString(is));
        } else {
            trace("key=" + item.getFieldName());
            InputStream is = item.openStream();

            // InputStream を使ってメモリに展開するなり、
            // ファイルに書き込むなり・・・
            int len = 0;
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream("foo.dat");

            try {
                while((len = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
            } finally {
                fos.close();
            }
        }
    }
}