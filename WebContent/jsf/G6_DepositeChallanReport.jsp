<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">

			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
						<h2>
							<h:outputText value=" G6_Deposite Challan Report"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{g6_DepositeChallanReportAction.radio}"
							valueChangeListener="#{g6_DepositeChallanReportAction.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="DWR" itemLabel="District Wise Revenue" />
							<f:selectItem itemValue="HWR" itemLabel="Head Wise Revenue" />
							<f:selectItem itemValue="MWR" itemLabel="Monthly Wise Revenue" />

						</h:selectOneRadio>
					</div>
				</div>



<rich:spacer height="10px;"></rich:spacer>

<div class="row" align="center">
<div class="col-md-12">
<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%;" 
onchange="this.form.submit();" value="#{g6_DepositeChallanReportAction.year}">
<f:selectItem itemLabel="2019-20" itemValue="19"/>
<f:selectItem itemLabel="2020-21" itemValue="21"/>
</h:selectOneRadio>
</div>


</div>




				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">

								<h:outputText style="FONT-SIZE: 15px; FONT-WEIGHT: bold;"
						value="District :" rendered="#{g6_DepositeChallanReportAction.radio eq 'DWR'}" ></h:outputText>
										
										<h:selectOneMenu style="width: 200px; height:25px;" rendered="#{g6_DepositeChallanReportAction.radio eq 'DWR'}"  
											value="#{g6_DepositeChallanReportAction.districtID}"
											valueChangeListener="#{g6_DepositeChallanReportAction.radioListener}"
											onchange="this.form.submit();" >
											<f:selectItems
												value="#{g6_DepositeChallanReportAction.districtList}" />
										</h:selectOneMenu>


				</div>


				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br/> 
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{g6_DepositeChallanReportAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{g6_DepositeChallanReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{g6_DepositeChallanReportAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{g6_DepositeChallanReportAction.reset}">
					</h:commandButton> 
					
				</div>
				<br />
				 <div class="col-md-12" align="center">
					<h:commandButton action="#{wholesaleStockReviewAction.excel}"
						styleClass="btn btn-primary" value="Generate Excel"
						rendered="false" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{wholesaleStockReviewAction.exlname}.xls"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{wholesaleStockReviewAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				</div>
				<br/>
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>