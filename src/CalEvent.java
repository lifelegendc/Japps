import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class CalEvent extends Applet implements ActionListener {
	Panel pl, p2;
	TextField r;
	String content = "0";
	boolean flgp = true; // �Ƿ�Ϊ����
	boolean flgd = false; // �Ƿ�����С����
	boolean flgE0 = false; // �����Ƿ�Ϊ��
	double[] num = new double[3]; // �洢���������������
	Button[] btn = new Button[10];
	Button bd = new Button(".");
	Button bn = new Button("+/-");
	Button[] btn2 = new Button[8];
	String[] func = { "+", "sqrt", "-", "CE", "*", "<--", "/", "=" };
	String oper = "null"; // ��¼���µ������
	int cmpNum = 0; // ��¼��ǰ�����������ʲôλ��
	String sng = "null";// ��¼��ǰ���µ����������+ - * / =
	String crnt = "null";// ���ٵ�ǰ��������������"n"���������"s"�� nǰ��������n2������s�����

	public void init() {

		for (int i = 7; i >= 1; i -= 6) {
			for (int j = 0; j < 3; j++, i++)
				btn[i] = new Button(i + "");
		}
		btn[0] = new Button("0");
		for (int i = 0; i < 8; i++)
			btn2[i] = new Button(func[i]);
		setLayout(new BorderLayout());
		pl = new Panel(new GridLayout(4, 3));
		p2 = new Panel(new GridLayout(4, 2));
		r = new TextField("0");
		for (int i = 7; i >= 1; i -= 6) {
			for (int j = 0; j < 3; j++, i++)
				pl.add(btn[i]);
		}
		pl.add(btn[0]);
		pl.add(bd);
		pl.add(bn);
		for (int i = 0; i < 8; i++)
			p2.add(btn2[i]);
		add(pl, BorderLayout.WEST);
		add(p2, BorderLayout.EAST);
		add(r, BorderLayout.NORTH);
		setSize(250, 250);
		for (int i = 0; i < 10; i++)
			btn[i].addActionListener(this);
		bd.addActionListener(this);
		bn.addActionListener(this);
		for (int i = 0; i < 8; i++)
			btn2[i].addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 10; i++) {// �������ּ���
			if (e.getSource() == btn[i]) {
				crnt = "n";

				if (content.startsWith("0") & !flgd) // content.substring(0,1)=="0"
														// ����
					// �����ͷ������0����������0ʼ����0
					content = content.substring(1); // ��ȥ��

				content = content + e.getActionCommand();
				r.setText(content);
			}
		}
		// if(e.getKeyCode()==VK_ENTER){Ҫ��ʲô}
		if (e.getSource() == bd)// С����
		{
			if (!flgd) {
				content = content + e.getActionCommand();
				r.setText(content);
			}
			flgd = true;// һ������ֻ��һ��С����
		}
		if (e.getSource() == bn)// ���
		{
			if (!content.equals("0")) {// 0���ñ��
				if (flgp)// �������������
				{
					content = "-" + content;
					r.setText(content);
				} else if (!flgp)// ��������Ǹ���
				{
					content = content.substring(1);
					r.setText(content);
				}
				flgp = !flgp;
			}
		}
		// -----------------------------------------�ұ߰��
		for (int i = 0; i < 8; i = i + 2) {
			if (e.getSource() == btn2[i]) { // ѡ�����
				if (crnt.equals("s"))
					break;
				r.setText(e.getActionCommand());
				num[cmpNum] = Double.parseDouble(content);// 1
				if (sng == "=") // �ڶ�����������ʱ������һ�ν������num[0]
					num[cmpNum] = num[2];
				if (oper != "null") {// ��������ʱ
					// System.out.println(num[cmpNum]);
					switch (oper) {
					case "+":
						num[2] = num[0] + num[1];
						break;
					case "-":
						num[2] = num[0] - num[1];
						break;
					case "*":
						num[2] = num[0] * num[1];
						break;
					case "/": {
						if (num[1] == 0)// ��ĸ��Ϊ0���ж�
						{
							r.setText("��������Ϊ��");
							flgE0 = true;
							break;
						} else
							num[2] = num[0] / num[1];
					}
						break;
					default:
						break;
					}
					cmpNum = 0;
					num[0] = num[2];
					content = num[2] + "";// �ȼ��㣬Ȼ��ѽ������
					r.setText(content);
				}
				oper = e.getActionCommand();
				switch (oper) {
				case "+":
				case "-":
				case "*":
				case "/": {
					crnt = "s";
					if (cmpNum != 2)
						cmpNum++;
					else if (cmpNum == 2)
						cmpNum = 0;
					sng = oper;
					content = "0";
					flgp = true;// Ϊ��������������׼��
					flgd = false;
				}
					break;
				default:
					break;
				}
			}
		}
		if (e.getSource() == btn2[7])// =
		{
			crnt = "n2";// ��ʾ���µȺţ�����������������
			sng = "=";
			flgp = true;// Ϊ��һ�ּ�������׼��
			flgE0 = false;
			flgd = false; // �Ƿ�����С����
			num[cmpNum] = Double.parseDouble(content);
			switch (oper) {
			case "+":
				num[2] = num[0] + num[1];
				break;
			case "-":
				num[2] = num[0] - num[1];
				break;
			case "*":
				num[2] = num[0] * num[1];
				break;
			case "/": {
				if (num[1] == 0)// ��ĸ��Ϊ0���ж�
				{
					r.setText("��������Ϊ��");
					flgE0 = true;
					break;
				} else
					num[2] = num[0] / num[1];
			}
				break;
			default:
				break;
			}
			if (!flgE0) {
				content = num[2] + "";
				r.setText(content);
			}
			cmpNum = 0;
			oper = "null";
			sng = "null";
		}
		if (e.getSource() == btn2[1])// sqrt //���ݱ䣬���ֱ�
		{
			if (content.substring(0, 1).equals("-"))// �������ܿ���
				r.setText("��Ч����");
			else {
				content = Math.sqrt(Double.parseDouble(content)) + "";
				num[cmpNum] = Math.sqrt(Double.parseDouble(content));
				r.setText(content);
			}
		}
		if (e.getSource() == btn2[3])// CE
		{
			sng = "null";
			flgp = true;// Ϊ��������������׼��
			flgE0 = false;
			flgd = false;
			crnt = "null";
			for (int i = 0; i < 3; i++)
				// ��ռ�����ʷ
				num[i] = 0;
			cmpNum = 0;
			content = "0";
			r.setText(content);
		}
		if (e.getSource() == btn2[5])// <---//���ݱ䣬���ֱ�
		{
			// ���һλ�����˸�Ļ�ֱ���˻��㣬����Ҳ�ǣ����硰-7��
			if (content.length() != 2 & !(content.equals("0")))
				content = content.substring(0, content.length() - 1);

			else if (content.length() == 2)
				if (content.substring(0, 1).equals("-"))
					content = "0";
				else {
					content = content.substring(0, content.length() - 1);
				}
			if (content.length() != 0) {
				r.setText(content);
				num[cmpNum] = Double.parseDouble(content);
			} else {
				content = "0";
				r.setText(content);
				num[cmpNum] = 0;
			}
			if (content.equals("0")) { // �����Ϊ0
				flgp = true;// Ϊ��һ�ּ�������׼��
				flgd = false; // �Ƿ�����С����
			}
			for (int i = 0; i < content.length() - 1; i++)
				// �˸��С����Ҳ�˵��˵Ļ����޸�С�����־
				if (!(content.substring(i, i + 1).equals(".")))
					flgd = false; // �Ƿ�����С����

		}
	}
}

// 11.25 ����ɡ���������ʵ��
// ����ɣ���������
// 11.26 ����ɡ����ƹ���0��С��������⣬�����˸����жϺͳ����жϣ���������,������������ŵ�����,�˸�����С����ͱ�ŵ�����
// 11.27����ɡ�ʵ��������������ͬʱ��������
//����ɣ�BigDecimal ��������ʱdouble˫���ȳ��ֵ�С���㲻����׼������
