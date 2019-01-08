/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celllife.idart.gui.reportParameters;

import java.util.Calendar;
import java.util.Date;
import model.manager.AdministrationManager;
import model.manager.reports.AbandonosIdartOpenMRSReport;
import model.manager.reports.LostToFollowUpReturnedReport;
import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.hibernate.Clinic;
import org.celllife.idart.gui.platform.GenericReportGui;
import static org.celllife.idart.gui.platform.GenericReportGuiInterface.REPORTTYPE_CLINICMANAGEMENT;
import static org.celllife.idart.gui.platform.GenericReportGuiInterface.REPORT_LOST_TO_FOLLOW_UP_RETURNED;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartColor;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.misc.iDARTUtil;
import org.celllife.idart.model.utils.PackageLifeStage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.vafada.swtcalendar.SWTCalendar;
import org.vafada.swtcalendar.SWTCalendarListener;

/**
 *
 * @author colaco
 */
public class AbandonosIdartOpenMRS extends GenericReportGui {

	private Group grpClinicSelection;

	private Label lblClinic;

	private CCombo cmbClinic;

	private SWTCalendar calendarStart;

	private SWTCalendar calendarEnd;

	private Group grpDateRange;

	private Label lblStartDate;

	private Label lblEndDate;

	private PackageLifeStage packageStage;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            Shell
	 * @param activate
	 *            boolean
	 */

	public AbandonosIdartOpenMRS(Shell parent, boolean activate) {
		super(parent, REPORTTYPE_CLINICMANAGEMENT, activate);
	}

	@Override
	protected void createShell() {
                buildShell(REPORT_CONFIRMACA_ABANDONOS_OPENMRS, new Rectangle(70, 50,700, 500));
		// create the composites
		createMyGroups();

	}

	private void createMyGroups() {
		createGrpClinicSelection();
		createGrpDateRange();
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
                iDartImage icoImage = iDartImage.REPORT_PATIENTDEFAULTERS;
		buildCompdHeader(REPORT_CONFIRMACA_ABANDONOS_OPENMRS, icoImage);
	}

	/**
	 * This method initializes grpClinicSelection
	 * 
	 */
	private void createGrpClinicSelection() {

		grpClinicSelection = new Group(getShell(), SWT.NONE);
		grpClinicSelection.setText("");
		grpClinicSelection.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpClinicSelection.setBounds(new Rectangle(151, 83, 386, 60));

		lblClinic = new Label(grpClinicSelection, SWT.NONE);
		lblClinic.setBounds(new Rectangle(59, 25, 100, 20));
		lblClinic.setText("Seleccione a Unidade Sanitaria:");
		lblClinic.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		cmbClinic = new CCombo(grpClinicSelection, SWT.BORDER);
		cmbClinic.setBounds(new Rectangle(169, 25, 176, 20));
		cmbClinic.setEditable(false);
		cmbClinic.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		cmbClinic.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
		CommonObjects.populateClinics(getHSession(), cmbClinic);

	}

	/**
	 * This method initializes grpDateRange
	 * 
	 */
	private void createGrpDateRange() {

		grpDateRange = new Group(getShell(), SWT.NONE);
		grpDateRange.setText("Periodos:");
		grpDateRange.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpDateRange.setBounds(new Rectangle(68, 180, 520, 201));
		grpDateRange.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		lblStartDate = new Label(grpDateRange, SWT.CENTER | SWT.BORDER);
		lblStartDate.setBounds(new org.eclipse.swt.graphics.Rectangle(40, 30,
				180, 20));
		lblStartDate.setText("Seleccione a Data Inicio:");
		lblStartDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		lblEndDate = new Label(grpDateRange, SWT.CENTER | SWT.BORDER);
		lblEndDate.setBounds(new org.eclipse.swt.graphics.Rectangle(300, 30,
				180, 20));
		lblEndDate.setText("Seleccione a Data Fim");
		lblEndDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		calendarStart = new SWTCalendar(grpDateRange);
		calendarStart.setBounds(20, 55, 220, 140);

		calendarEnd = new SWTCalendar(grpDateRange);
		calendarEnd.setBounds(280, 55, 220, 140);

	}

	/**
	 * Method getCalendarStart.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendarStart() {
		return calendarStart.getCalendar();
	}

	/**
	 * Method getCalendarEnd.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendarEnd() {
		return calendarEnd.getCalendar();
	}

	/**
	 * Method setStartDate.
	 * 
	 * @param date
	 *            Date
	 */
	public void setStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarStart.setCalendar(calendar);
	}

	/**
	 * Method setEndDate.
	 * 
	 * @param date
	 *            Date
	 */
	public void setEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarEnd.setCalendar(calendar);
	}

	/**
	 * Method addStartDateChangedListener.
	 * 
	 * @param listener
	 *            SWTCalendarListener
	 */
	public void addStartDateChangedListener(SWTCalendarListener listener) {

		calendarStart.addSWTCalendarListener(listener);
	}

	/**
	 * Method addEndDateChangedListener.
	 * 
	 * @param listener
	 *            SWTCalendarListener
	 */
	public void addEndDateChangedListener(SWTCalendarListener listener) {

		calendarEnd.addSWTCalendarListener(listener);
	}

	/**
	 * This method initializes compButtons
	 * 
	 */
	@Override
	protected void createCompButtons() {
	}

	@Override
	protected void cmdViewReportWidgetSelected() {
		if (fieldsOk()) {
			Clinic c = AdministrationManager.getClinic(getHSession(), cmbClinic
					.getText().trim());

			                 AbandonosIdartOpenMRSReport report = new AbandonosIdartOpenMRSReport(
					getShell(), c.getClinicName(), calendarStart.getCalendar().getTime(),
					calendarEnd.getCalendar().getTime());
			viewReport(report);
		}
	}

	/**
	 * This method is called when the user presses "Close" button
	 * 
	 */
	@Override
	protected void cmdCloseWidgetSelected() {
		cmdCloseSelected();
	}

	@Override
	protected void setLogger() {
		setLog(Logger.getLogger(this.getClass()));
	}

	/**
	 * Method setPackageStage.
	 * 
	 * @param packageStage
	 *            PackageLifeStage
	 */
	public void setPackageStage(PackageLifeStage packageStage) {
		this.packageStage = packageStage;
	}

	private boolean fieldsOk() {

		if (iDARTUtil.before(calendarEnd.getCalendar().getTime(), calendarStart
				.getCalendar().getTime())) {

			MessageBox mbox = new MessageBox(getShell(), SWT.ICON_ERROR
					| SWT.OK);
			mbox.setText("Data Fim Invalida");
			mbox.setMessage("Seleccione uma data fim maior ou igual a data inicio.");
			mbox.open();

			return false;
		}

		if (cmbClinic.getText().equals("")) {

			MessageBox missing = new MessageBox(getShell(), SWT.ICON_ERROR
					| SWT.OK);
			missing.setText("Nenhuma Unidade Sanitaria Seleccionada");
			missing
			.setMessage("Nenhuma Unidade Sanitaria Seleccionada. Seleccione a Unidade Sanitaria.");
			missing.open();

			return false;

		}

		return true;
	}
}
