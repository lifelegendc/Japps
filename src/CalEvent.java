import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class CalEvent extends Applet implements ActionListener {
	Panel pl, p2;
	TextField r;
	String content = "0";
	boolean flgp = true; // 是否为正数
	boolean flgd = false; // 是否已有小数点
	boolean flgE0 = false; // 除数是否为零
	double[] num = new double[3]; // 存储参与运算的两个数
	Button[] btn = new Button[10];
	Button bd = new Button(".");
	Button bn = new Button("+/-");
	Button[] btn2 = new Button[8];
	String[] func = { "+", "sqrt", "-", "CE", "*", "<--", "/", "=" };
	String oper = "null"; // 记录按下的运算符
	int cmpNum = 0; // 记录当前处理的数据是什么位置
	String sng = "null";// 记录当前按下的运算符（）+ - * / =
	String crnt = "null";// 跟踪当前操作（输入数据"n"还是运算符"s"） n前两个数，n2表结果，s表符号

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
		for (int i = 0; i < 10; i++) {// 按下数字键盘
			if (e.getSource() == btn[i]) {
				crnt = "n";

				if (content.startsWith("0") & !flgd) // content.substring(0,1)=="0"
														// 不行
					// 如果开头数字是0，则连续按0始终是0
					content = content.substring(1); // 先去零

				content = content + e.getActionCommand();
				r.setText(content);
			}
		}
		// if(e.getKeyCode()==VK_ENTER){要做什么}
		if (e.getSource() == bd)// 小数点
		{
			if (!flgd) {
				content = content + e.getActionCommand();
				r.setText(content);
			}
			flgd = true;// 一个数里只有一个小数点
		}
		if (e.getSource() == bn)// 变号
		{
			if (!content.equals("0")) {// 0不用变号
				if (flgp)// 如果现在是正数
				{
					content = "-" + content;
					r.setText(content);
				} else if (!flgp)// 如果现在是负数
				{
					content = content.substring(1);
					r.setText(content);
				}
				flgp = !flgp;
			}
		}
		// -----------------------------------------右边板块
		for (int i = 0; i < 8; i = i + 2) {
			if (e.getSource() == btn2[i]) { // 选择符号
				if (crnt.equals("s"))
					break;
				r.setText(e.getActionCommand());
				num[cmpNum] = Double.parseDouble(content);// 1
				if (sng == "=") // 第二次连续计算时，把上一次结果传入num[0]
					num[cmpNum] = num[2];
				if (oper != "null") {// 连续计算时
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
						if (num[1] == 0)// 分母不为0的判断
						{
							r.setText("除数不能为零");
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
					content = num[2] + "";// 先计算，然后把结果传入
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
					flgp = true;// 为新数据输入做好准备
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
			crnt = "n2";// 表示按下等号，接下来处理结果数据
			sng = "=";
			flgp = true;// 为新一轮计算做好准备
			flgE0 = false;
			flgd = false; // 是否已有小数点
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
				if (num[1] == 0)// 分母不为0的判断
				{
					r.setText("除数不能为零");
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
		if (e.getSource() == btn2[1])// sqrt //内容变，数字变
		{
			if (content.substring(0, 1).equals("-"))// 负数不能开根
				r.setText("无效输入");
			else {
				content = Math.sqrt(Double.parseDouble(content)) + "";
				num[cmpNum] = Math.sqrt(Double.parseDouble(content));
				r.setText(content);
			}
		}
		if (e.getSource() == btn2[3])// CE
		{
			sng = "null";
			flgp = true;// 为新数据输入做好准备
			flgE0 = false;
			flgd = false;
			crnt = "null";
			for (int i = 0; i < 3; i++)
				// 清空计算历史
				num[i] = 0;
			cmpNum = 0;
			content = "0";
			r.setText(content);
		}
		if (e.getSource() == btn2[5])// <---//内容变，数字变
		{
			// 最后一位数按退格的话直接退回零，负数也是，比如“-7”
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
			if (content.equals("0")) { // 如果变为0
				flgp = true;// 为新一轮计算做好准备
				flgd = false; // 是否已有小数点
			}
			for (int i = 0; i < content.length() - 1; i++)
				// 退格把小数点也退掉了的话，修改小数点标志
				if (!(content.substring(i, i + 1).equals(".")))
					flgd = false; // 是否已有小数点

		}
	}
}

// 11.25 【完成】基本功能实现
// 待完成：连续计算
// 11.26 【完成】完善关于0和小数点的问题，增加了根号判断和除数判断，连续计算,连续按运算符号的问题,退格引起小数点和变号的问题
// 11.27【完成】实现连续多个运算符同时参与运算
//待完成：BigDecimal 连续运算时double双精度出现的小数点不够精准的问题
