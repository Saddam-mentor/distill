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

				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row">
					<div align="center">
						<h:outputText value="Sale AND  Duty From Wholesale"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div></div>


				<div class="row">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<div class="col-md=12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">


					<h:outputText>Form Date : </h:outputText>

					<rich:calendar value="#{saleAND_DutyFromWholesaleAction.formdate}"></rich:calendar>
					<rich:spacer width="30px"></rich:spacer>
					<h:outputText>To Date : </h:outputText>
					<rich:calendar value="#{saleAND_DutyFromWholesaleAction.todate}"></rich:calendar>

				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #c3e1f4;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio style="FONT-WEIGHT: bold; "
							value="#{saleAND_DutyFromWholesaleAction.radioType}"
							onchange="this.form.submit();">

							<f:selectItem itemValue="C" itemLabel="Consolidated" />
							<f:selectItem itemValue="D" itemLabel="Detailed" />
						</h:selectOneRadio>
					</div>
				</div>

				<rich:spacer height="20px"></rich:spacer>
				<div class="row" align="center">

					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{saleAND_DutyFromWholesaleAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{saleAND_DutyFromWholesaleAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{saleAND_DutyFromWholesaleAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

				</div>

				<rich:spacer height="20px"></rich:spacer>
				<div class="col-md-12" align="center">


					<h:commandButton action="#{saleAND_DutyFromWholesaleAction.excel}"
						value="Generate Excel"
						style="background-color:#C5C5C5; font-size: large;" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{saleAND_DutyFromWholesaleAction.exlname}"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{saleAND_DutyFromWholesaleAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<h:commandButton
						action="#{saleAND_DutyFromWholesaleAction.reset}"
						value="Reset" style="background-color:#C5C5C5; font-size: large;" />

				</div>
				<br /> <br />


			</div>
		</h:form>
	</f:view>

</ui:composition>