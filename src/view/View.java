package view;
import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class View extends JFrame {

    private NhanVien nhanVien;
    // 3 CỘT CHÍNH
    private JPanel colLeft, colCenter, colRight;
    // LEFT
    private JPanel panelGioHang;
    private JLabel lblTongTien;
    private Map<String, Integer> gioHang = new HashMap<>();
    // CENTER
    private JPanel panelSanPham;
    private java.util.List<SanPham> dsSanPham = new ArrayList<>();
    // RIGHT
    private JPanel panelDanhMuc;
    public View(NhanVien nv) {
        this.nhanVien = nv;
        setTitle("SALES SYSTEM");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);  // KHÔNG CHO LỆCH UI
        taoHeader();
        taoLayout3Cot();
        loadData();
        setVisible(true);
    }
    // HEADER
    private void taoHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel lblNgay = new JLabel(
        LocalDate.now().format(
        DateTimeFormatter.ofPattern("'Ngày: 'dd/MM/yyyy")
       )
    );

        lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lblNV = new JLabel("Nhân viên: " + nhanVien.getTenNV());
        lblNV.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(lblNgay, BorderLayout.WEST);
        header.add(lblNV, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    // 3 CỘT
    
    private void taoLayout3Cot() {

        JPanel main = new JPanel(null);
        add(main, BorderLayout.CENTER);

        int W = 1400;
        int H = 720;

        int wLeft   = (int)(W * 0.40);
        int wCenter = (int)(W * 0.40);
        int wRight  = (int)(W * 0.20);


        // GIỎ HÀNG BÊN TRÁI
        colLeft = new JPanel(null);
        colLeft.setBounds(0, 0, wLeft, H);
        colLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        main.add(colLeft);

        JScrollPane scGio = new JScrollPane();
        scGio.setBounds(10, 10, wLeft - 20, H - 150);
        scGio.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        colLeft.add(scGio);

        panelGioHang = new JPanel();
        panelGioHang.setLayout(new BoxLayout(panelGioHang, BoxLayout.Y_AXIS));
        scGio.setViewportView(panelGioHang);

        lblTongTien = new JLabel("Tổng: 0 đ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setBounds(10, H - 135, 300, 30);
        colLeft.add(lblTongTien);

        JButton btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setBounds(10, H - 95, wLeft - 20, 70);
        btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnThanhToan.setBackground(new Color(180, 220, 240));
        btnThanhToan.addActionListener(e -> xuLyThanhToan());
        colLeft.add(btnThanhToan);


        //SẢN PHẨM Ở GIỮA
        colCenter = new JPanel(null);
        colCenter.setBounds(wLeft, 0, wCenter, H);
        colCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        main.add(colCenter);

        JScrollPane scSP = new JScrollPane();
        scSP.setBounds(10, 10, wCenter - 20, H - 20);
        scSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        colCenter.add(scSP);

        panelSanPham = new JPanel(null);
        scSP.setViewportView(panelSanPham);

        // DANH MỤC BÊN PHẢI
        colRight = new JPanel(new BorderLayout());
        colRight.setBounds(wLeft + wCenter, 0, wRight, H);
        colRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        main.add(colRight);

        panelDanhMuc = new JPanel();
        panelDanhMuc.setLayout(new BoxLayout(panelDanhMuc, BoxLayout.Y_AXIS));
        panelDanhMuc.setBorder(new EmptyBorder(10, 10, 10, 10)); 


        JScrollPane scDM = new JScrollPane(panelDanhMuc);
        scDM.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scDM.setBorder(new EmptyBorder(10, 10, 10, 10));
        colRight.add(scDM, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

            // NÚT TIỆN ÍCH
        JButton btnTienIch = new JButton("Hóa đơn");
        btnTienIch.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTienIch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnTienIch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnTienIch.addActionListener(e -> moTienIch());
        bottomPanel.add(btnTienIch);
        bottomPanel.add(Box.createVerticalStrut(10)); 

        // NÚT ĐĂNG XUẤT
         JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogout.addActionListener(e -> {
        dispose();
        new LoginView().setVisible(true);
});
        bottomPanel.add(btnLogout);

        colRight.add(bottomPanel, BorderLayout.SOUTH);
   }
 
    private void loadData() {
        dsSanPham = SanPhamDAO.getAllSanPham();
        loadDanhMuc();
        veSanPham(dsSanPham);
    }

    private void loadDanhMuc() {

        panelDanhMuc.removeAll();

        Set<String> loaiSet = new HashSet<>();
        for (SanPham sp : dsSanPham) loaiSet.add(sp.getLoai());

        for (String loai : loaiSet) {

            JButton btn = new JButton(loai);
            btn.setMaximumSize(new Dimension(9999, 45));

            btn.addActionListener(e -> {
                java.util.List<SanPham> list = new ArrayList<>();
                for (SanPham sp : dsSanPham) {
                    if (sp.getLoai().equals(loai)) list.add(sp);
                }
                veSanPham(list);
            });

            panelDanhMuc.add(btn);
            panelDanhMuc.add(Box.createVerticalStrut(10));
        }

        panelDanhMuc.revalidate();
        panelDanhMuc.repaint();
    }


    private void veSanPham(java.util.List<SanPham> list) {

        panelSanPham.removeAll();

        int w = colCenter.getWidth() - 40;
        int cellW = w / 2 - 10;
        int cellH = 130;

        int x = 10, y = 10, col = 0;

        for (SanPham sp : list) {

            JPanel p = taoOsp(sp);
            p.setBounds(x, y, cellW, cellH);
            panelSanPham.add(p);

            col++;
            if (col == 2) {
                col = 0;
                x = 10;
                y += cellH + 20;
            } else {
                x += cellW + 20;
            }
        }

        panelSanPham.setPreferredSize(new Dimension(w, y + 150));
        panelSanPham.revalidate();
        panelSanPham.repaint();
    }


    private JPanel taoOsp(SanPham sp) {

        JPanel p = new JPanel(null);
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel ten = new JLabel(sp.getTenSp());
        ten.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ten.setBounds(10, 10, 300, 25);
        p.add(ten);

        JLabel gia = new JLabel(String.format("%,.0f đ", sp.getGia()));
        gia.setBounds(10, 40, 150, 20);
        p.add(gia);

        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                themVaoGio(sp);
            }
        });

        return p;
    }
    private void themVaoGio(SanPham sp) {

        gioHang.put(sp.getMaSP(), gioHang.getOrDefault(sp.getMaSP(), 0) + 1);

        veGioHang();
        capNhatTong();
    }

    // THIẾT KẾ GIAO DIỆN GIỎ HÀNG
    private void veGioHang() {

        panelGioHang.removeAll();
        int w = colLeft.getWidth() - 40;
        for (SanPham sp : dsSanPham) {

            if (!gioHang.containsKey(sp.getMaSP())) continue;

            int sl = gioHang.get(sp.getMaSP());
            JPanel item = new JPanel(null);
            item.setPreferredSize(new Dimension(w, 80));
            JLabel ten = new JLabel(sp.getTenSp());
            ten.setFont(new Font("Segoe UI", Font.BOLD, 16));
            ten.setBounds(10, 5, w - 120, 25);
            item.add(ten);
            JLabel gia = new JLabel(String.format("%,.0f đ", sp.getGia() * sl));
            gia.setBounds(10, 35, 150, 20);
            item.add(gia);
            JLabel lblSL = new JLabel("SL: " + sl);
            lblSL.setBounds(150, 35, 70, 20);
            item.add(lblSL);
            JLabel xoa = new JLabel("[Xóa]");
            xoa.setForeground(Color.RED);
            xoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
            xoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            xoa.setBounds(w - 70, 5, 50, 25);
            xoa.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gioHang.remove(sp.getMaSP());
                    veGioHang();
                    capNhatTong();
                }
            });

            item.add(xoa);
            panelGioHang.add(item);
        }
        panelGioHang.revalidate();
        panelGioHang.repaint();
    }
    private void capNhatTong() {
        double tong = 0;
        for (SanPham sp : dsSanPham) {
            if (gioHang.containsKey(sp.getMaSP())) {
                tong += sp.getGia() * gioHang.get(sp.getMaSP());
            }
        }
        lblTongTien.setText("Tổng: " + String.format("%,.0f", tong) + " đ");
    }
    
    private void xuLyThanhToan() {

        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }
        String maHD = taoMaHD();
        double tongTien = 0;
        for (SanPham sp : dsSanPham) {
            if (gioHang.containsKey(sp.getMaSP())) {
                tongTien += sp.getGia() * gioHang.get(sp.getMaSP());
            }
        }      
        HoaDon hd = new HoaDon(maHD, new java.util.Date(), tongTien);
        HoaDonDAO.luuHoaDon(hd);    
        java.util.List<ChiTietHoaDon> cts = new ArrayList<>();
        for (SanPham sp : dsSanPham) {
            if (gioHang.containsKey(sp.getMaSP())) {
                int sl = gioHang.get(sp.getMaSP());
                cts.add(new ChiTietHoaDon(maHD, sp.getMaSP(), sl, sp.getGia() * sl));
            }
        }
        ChiTietHoaDonDAO.luuChiTietHoaDon(cts);
        JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
        gioHang.clear();
        veGioHang();
        capNhatTong();
    }
    private String taoMaHD() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int so = HoaDonDAO.getAll().size() + 1;
        return "HD" + date + "-" + String.format("%03d", so);
    }
    
    
    // TẠO HÀM TIỆN ÍCH ĐỂ XEM TỔNG DOANH THU TRONG NGÀY, SỐ ĐƠN ĐÃ BÁN VÀ CHI TIẾT HÓA ĐƠN NGAY TRÊN POS MÀ KO CẦN THÔNG QUA ACCESS
    private void moTienIch() {

        JDialog dlg = new JDialog(this, "Hóa đơn bán hàng", true);
        dlg.setSize(650, 600);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(null);
        JLabel lblNgay = new JLabel("Ngày: " + LocalDate.now());
        lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNgay.setBounds(20, 20, 400, 30);
        dlg.add(lblNgay);
        java.util.List<HoaDon> ds = HoaDonDAO.getAllToday();
        JLabel lblSoDon = new JLabel("Số đơn đã bán: " + ds.size());
        lblSoDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSoDon.setBounds(20, 60, 400, 25);
        dlg.add(lblSoDon);
        double tongNgay = 0;
        for (HoaDon hd : ds) tongNgay += hd.getTongTien();
        JLabel lblTongNgay = new JLabel("Tổng tiền: " + String.format("%,.0f đ", tongNgay));
        lblTongNgay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongNgay.setBounds(20, 90, 400, 25);
        dlg.add(lblTongNgay);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (HoaDon hd : ds) {
            model.addElement(hd.getMaHD() + "  -  " + String.format("%,.0f đ", hd.getTongTien()));
        }
        JList<String> listHD = new JList<>(model);
        JScrollPane sp = new JScrollPane(listHD);
        sp.setBounds(20, 140, 600, 330);
        dlg.add(sp);
        listHD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listHD.getSelectedIndex();
                    if (index >= 0) {
                        moChiTietHoaDon(ds.get(index));
                    }
                }
            }
        });
        dlg.setVisible(true);
    }  
    // HÀM CHI TIẾT HÓA ĐƠN ĐỂ XEM CHI TIẾT HÓA ĐƠN ĐÃ BÁN
    private void moChiTietHoaDon(HoaDon hd) {
        
    JDialog dlg = new JDialog(this, "Chi tiết hóa đơn", true);
    dlg.setSize(750, 550);   // rộng hơn, đẹp hơn
    dlg.setLocationRelativeTo(this);
    dlg.setLayout(null);
    // TIÊU ĐỀ 
    JLabel lblMa = new JLabel("Hóa đơn: " + hd.getMaHD());
    lblMa.setFont(new Font("Segoe UI", Font.BOLD, 22));
    lblMa.setBounds(20, 20, 600, 30);
    dlg.add(lblMa);

    JLabel lblNgay = new JLabel("Ngày: " + hd.getNgayLap());
    lblNgay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    lblNgay.setBounds(20, 55, 400, 25);
    dlg.add(lblNgay);

    // LẤY DỮ LIỆU
    java.util.List<ChiTietHoaDon> dsct = ChiTietHoaDonDAO.getByMaHD(hd.getMaHD());
    String[] col = {"Sản phẩm", "SL", "Thành tiền"};
    Object[][] data = new Object[dsct.size()][3];
    int i = 0;
    for (ChiTietHoaDon ct : dsct) {
        SanPham sp = SanPhamDAO.getById(ct.getmaSp());
        data[i][0] = sp.getTenSp();
        data[i][1] = ct.getSoLuong();
        data[i][2] = String.format("%,.0f đ", ct.getThanhTien());
        i++;
    }

    JTable tb = new JTable(data, col);
    tb.setRowHeight(28);
    tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    JScrollPane sp = new JScrollPane(tb);
    sp.setBounds(20, 100, 700, 300);  
    dlg.add(sp);

    // TỔNG TIỀN 
    JLabel lblTong = new JLabel("Tổng cộng: " + String.format("%,.0f đ", hd.getTongTien()));
    lblTong.setFont(new Font("Segoe UI", Font.BOLD, 20));
    lblTong.setBounds(20, 410, 400, 30);
    dlg.add(lblTong);

    // NÚT ĐÓNG 
    JButton btnClose = new JButton("Đóng");
    btnClose.setBounds(300, 455, 120, 35);  
    btnClose.setFont(new Font("Segoe UI", Font.BOLD, 15));
    btnClose.addActionListener(e -> dlg.dispose());
    dlg.add(btnClose);
    dlg.setVisible(true);
}
}
