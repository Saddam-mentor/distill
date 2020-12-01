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
						<h:outputText value="Challan Deposit Detailed Report"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div>
					<h:inputHidden value="#{challanDepositDetailSuccessRptAction.hidden}"></h:inputHidden>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #c3e1f4;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  width: 58%; "
							value="#{challanDepositDetailSuccessRptAction.radioType}"
							valueChangeListener="#{challanDepositDetailSuccessRptAction.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="ALL" itemLabel="ALL" />
							<f:selectItem itemValue="SM" itemLabel="Sugarmill" />
							<f:selectItem itemValue="DL" itemLabel="Distillery" />
							<f:selectItem itemValue="BR" itemLabel="Brewery" />
							<f:selectItem itemValue="BWFL" itemLabel="BWFL" />
							<f:selectItem itemValue="FL2D" itemLabel="FL2D" />							
							<f:selectItem itemValue="NR" itemLabel="Non-Registered" />
							
						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Between Dates :
					<rich:calendar value="#{challanDepositDetailSuccessRptAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{challanDepositDetailSuccessRptAction.toDate}"></rich:calendar>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<br /> <br />
				<div class="row" align="center">

					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{challanDepositDetailSuccessRptAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{challanDepositDetailSuccessRptAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{challanDepositDetailSuccessRptAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
					<div class="col-md-12" align="center">


					<h:commandButton action="#{challanDepositDetailSuccessRptAction.excel}"
						value="Generate Excel" 
						style="background-color:#C5C5C5; font-size: large;" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{challanDepositDetailSuccessRptAction.exlname}"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel" 
							rendered="#{challanDepositDetailSuccessRptAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt; margin-left: 3px;"></h:outputText>
					</h:outputLink>



				</div>
				
					

					
				</div>
				
				
				
				

				<div class="row " align="center">
				
				<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{challanDepositDetailSuccessRptAction.reset}">
					</h:commandButton>
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>