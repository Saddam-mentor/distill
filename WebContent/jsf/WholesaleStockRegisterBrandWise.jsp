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
					<rich:spacer height="10px"></rich:spacer>					
				</div>	
				<div class="row">
					<div align="center">
						<h:outputText value="Wholesale Stock Register Report"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
					<h:inputHidden value="#{wholesaleStockRegisterBrandWiseAction.hidden}"></h:inputHidden>
				</div>				
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				
				
				<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{wholesaleStockRegisterBrandWiseAction.loginUserNm}"
							style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
				</div>
				
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{wholesaleStockRegisterBrandWiseAction.radioType}"
							valueChangeListener="#{wholesaleStockRegisterBrandWiseAction.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="FL2" itemLabel="FL2" />
							<f:selectItem itemValue="FL2B" itemLabel="FL2B" />
							<f:selectItem itemValue="CL2" itemLabel="CL2" />

						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Between Dates :
					<rich:calendar value="#{wholesaleStockRegisterBrandWiseAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{wholesaleStockRegisterBrandWiseAction.toDate}"></rich:calendar>
				</div>




				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>

				<div align="center">
					<h:outputText value="Select Brand:"
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu
						value="#{wholesaleStockRegisterBrandWiseAction.brandId}"
						style="height: 25px; width : 300px;">
						<f:selectItems
							value="#{wholesaleStockRegisterBrandWiseAction.brandList}" />
					</h:selectOneMenu>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<br /> <br />
				<div class="row" align="center">

					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{wholesaleStockRegisterBrandWiseAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{wholesaleStockRegisterBrandWiseAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{wholesaleStockRegisterBrandWiseAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{wholesaleStockRegisterBrandWiseAction.reset}">
					</h:commandButton>
				</div>
				
				<rich:spacer height="20px"></rich:spacer>
				<div class="col-md-12" align="center">


					<h:commandButton action="#{wholesaleStockRegisterBrandWiseAction.excel}"
						value="Generate Excel"
						style="background-color:#C5C5C5; font-size: large;" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{wholesaleStockRegisterBrandWiseAction.exlname}"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{wholesaleStockRegisterBrandWiseAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<h:commandButton
						action="#{wholesaleStockRegisterBrandWiseAction.reset}"
						value="Reset" style="background-color:#C5C5C5; font-size: large;" />

				</div>
				<br /> <br />
				

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>