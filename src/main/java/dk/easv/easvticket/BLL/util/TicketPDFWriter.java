package dk.easv.easvticket.BLL.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dk.easv.easvticket.BE.Ticket;


import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class TicketPDFWriter {

    private static final BaseColor DARK       = new BaseColor(26, 26, 46);
    private static final BaseColor ACCENT     = new BaseColor(90, 103, 216);
    private static final BaseColor LIGHT_GRAY = new BaseColor(245, 245, 248);
    private static final BaseColor MID_GRAY   = new BaseColor(140, 140, 150);
    private static final BaseColor WHITE      = BaseColor.WHITE;

    private static final Font FONT_TITLE    = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD,   WHITE);
    private static final Font FONT_SUBTITLE = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, new BaseColor(150, 150, 170));
    private static final Font FONT_LABEL    = new Font(Font.FontFamily.HELVETICA, 8,  Font.BOLD,   MID_GRAY);
    private static final Font FONT_VALUE    = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, DARK);
    private static final Font FONT_PRICE    = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD,   ACCENT);
    private static final Font FONT_TICKETID = new Font(Font.FontFamily.COURIER,   9,  Font.NORMAL, MID_GRAY);
    private static final Font FONT_FOOTER   = new Font(Font.FontFamily.HELVETICA, 8,  Font.NORMAL, MID_GRAY);

    public static File generatePDF(Ticket ticket) throws Exception {

        final String DIR = "src/main/resources/dk/easv/easvticket/tickets/";
        final String PATH_TO_PDF = DIR + ticket.getTicketId() + ".pdf";
        System.out.println("Variable to path declared and assigned");

        new File(DIR).mkdirs();

        Document document = new Document(PageSize.A5, 40, 40, 40, 40);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PATH_TO_PDF));
        document.open();

        PdfContentByte canvas = writer.getDirectContentUnder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm");

        // ── Header banner ────────────────────────────────────────────────
        Rectangle header = new Rectangle(0, PageSize.A5.getHeight() - 110, PageSize.A5.getWidth(), PageSize.A5.getHeight());
        header.setBackgroundColor(DARK);
        canvas.rectangle(header);

        document.add(new Paragraph(" "));

        Paragraph eventName = new Paragraph(ticket.getEvent().getName(), FONT_TITLE);
        eventName.setAlignment(Element.ALIGN_LEFT);
        eventName.setSpacingBefore(10);
        document.add(eventName);

        Paragraph ticketTypePara = new Paragraph(ticket.getTicketType().toString(), FONT_SUBTITLE);
        ticketTypePara.setSpacingAfter(50);
        document.add(ticketTypePara);

        // ── Details table ────────────────────────────────────────────────
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new float[]{1f, 1f});
        detailsTable.setSpacingAfter(16);

        addDetailCell(detailsTable, "DATE & TIME",  ticket.getEvent().getDate().format(formatter));
        addDetailCell(detailsTable, "LOCATION",     ticket.getEvent().getLocation().toString());
        addDetailCell(detailsTable, "CUSTOMER",     ticket.getCustomerName());
        addDetailCell(detailsTable, "EMAIL",        ticket.getEmail());

        document.add(detailsTable);

        // ── Divider ──────────────────────────────────────────────────────
        PdfPTable divider = new PdfPTable(1);
        divider.setWidthPercentage(100);
        divider.setSpacingAfter(16);
        PdfPCell dividerCell = new PdfPCell();
        dividerCell.setBorder(Rectangle.TOP);
        dividerCell.setBorderColorTop(new BaseColor(220, 220, 230));
        dividerCell.setBorderWidthTop(0.5f);
        dividerCell.setPadding(0);
        divider.addCell(dividerCell);
        document.add(divider);

        // ── Price + QR row ───────────────────────────────────────────────
        PdfPTable bottomTable = new PdfPTable(2);
        bottomTable.setWidthPercentage(100);
        bottomTable.setWidths(new float[]{1.2f, 1f});
        bottomTable.setSpacingAfter(16);

        // Price cell
        PdfPCell priceCell = new PdfPCell();
        priceCell.setBorder(Rectangle.NO_BORDER);
        priceCell.setPaddingTop(10);

        Paragraph priceLabel = new Paragraph("TOTAL PRICE", FONT_LABEL);
        Paragraph priceValue = new Paragraph(String.format("%.2f DKK", ticket.getPrice()), FONT_PRICE);
        priceValue.setSpacingBefore(4);

        Paragraph ticketIdLabel = new Paragraph("TICKET ID", FONT_LABEL);
        ticketIdLabel.setSpacingBefore(16);
        Paragraph ticketIdValue = new Paragraph(ticket.getTicketId(), FONT_TICKETID);
        ticketIdValue.setSpacingBefore(2);

        priceCell.addElement(priceLabel);
        priceCell.addElement(priceValue);
        priceCell.addElement(ticketIdLabel);
        priceCell.addElement(ticketIdValue);
        bottomTable.addCell(priceCell);

        // QR code cell
        PdfPCell qrCell = new PdfPCell();
        qrCell.setBorder(Rectangle.NO_BORDER);
        qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        try {
            java.awt.image.BufferedImage qrImage = QRGenerator.generate(TicketPayload.generate(ticket));
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(qrImage, "png", baos);
            Image qr = Image.getInstance(baos.toByteArray());
            qr.scaleAbsolute(110, 110);
            qrCell.addElement(qr);
        } catch (Exception e) {
            qrCell.addElement(new Paragraph("QR unavailable", FONT_FOOTER));
        }

        bottomTable.addCell(qrCell);
        document.add(bottomTable);

        // ── Footer ───────────────────────────────────────────────────────
        PdfPTable footer = new PdfPTable(1);
        footer.setWidthPercentage(100);
        PdfPCell footerCell = new PdfPCell();
        footerCell.setBackgroundColor(LIGHT_GRAY);
        footerCell.setBorder(Rectangle.NO_BORDER);
        footerCell.setPadding(10);

        Paragraph footerText = new Paragraph("This ticket is valid for one entry only. Please present this ticket at the entrance.", FONT_FOOTER);
        footerText.setAlignment(Element.ALIGN_CENTER);
        footerCell.addElement(footerText);
        footer.addCell(footerCell);
        document.add(footer);

        document.close();

        System.out.println("Before opening");

        return Path.of(PATH_TO_PDF).toFile();
    }

    private static void addDetailCell(PdfPTable table, String label, String value) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(LIGHT_GRAY);
        cell.setPadding(10);
        cell.setPaddingBottom(12);

        Paragraph labelPara = new Paragraph(label, FONT_LABEL);
        Paragraph valuePara = new Paragraph(value != null ? value : "—", FONT_VALUE);
        valuePara.setSpacingBefore(3);

        cell.addElement(labelPara);
        cell.addElement(valuePara);
        table.addCell(cell);
    }

}
