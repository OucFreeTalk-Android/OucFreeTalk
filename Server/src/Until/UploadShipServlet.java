package Until;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import net.sf.json.JSONObject;

public class UploadShipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String path;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8"); // ���ñ���
		// ��ô����ļ���Ŀ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// ��ȡ�ļ���Ҫ�ϴ�����·��
		String path = req.getRealPath("/upload");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		factory.setRepository(new File(path));
		// ���� ����Ĵ�С
		factory.setSizeThreshold(1024 * 1024);
		// �ļ��ϴ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// �����ϴ�����ļ�
			List<FileItem> list = upload
					.parseRequest(new ServletRequestContext(req));
			for (FileItem item : list) {
				// ��ȡ��������
				String name = item.getFieldName();
				// �����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ
				if (item.isFormField()) {
					// ��ȡ�û�����������ַ���,��Ϊ���ύ�������� �ַ������͵�
					String value = item.getString();
					req.setAttribute(name, value);
				} else {
					// ��ȡ·����
					String value = item.getName();
					// ���������һ����б��
					int start = value.lastIndexOf("\\");
					// ��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�
					String filename = value.substring(start + 1);
					req.setAttribute(name, filename);
					// д��������
					item.write(new File(path, filename));// �������ṩ��
					System.out.println("�ϴ��ɹ���" + filename);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("upload", filename);
					resp.getWriter().print(jsonObject.toString());// ��·�����ظ��ͻ���
				}
			}

		} catch (Exception e) {
			System.out.println("�ϴ�ʧ��");
			resp.getWriter().print("�ϴ�ʧ�ܣ�" + e.getMessage());
		}
	}
}