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
						<h:outputText value="Wholesale Stock Register Report"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div>
					<h:inputHidden value="#{wholesaleStockRegisterAction.hidden}"></h:inputHidden>
				</div>
				
	            <rich:spacer height="30px"></rich:spacer>
				<div class="row " align="center">
					 <div align="center">
					<h:outputText value="Select Year: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
                   <rich:spacer width="20px"></rich:spacer>
					<h:selectOneMenu
						value="#{wholesaleStockRegisterAction.year}"
						style="height: 25px; width : 150px;">
						<f:selectItems
							value="#{wholesaleStockRegisterAction.getAll_List}"/>
					</h:selectOneMenu>
				</div>
				</div>
                 <rich:spacer height="30px"></rich:spacer>
				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{wholesaleStockRegisterAction.radio}"
							valueChangeListener="#{wholesaleStockRegisterAction.radioListener}"
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
					<rich:calendar value="#{wholesaleStockRegisterAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{wholesaleStockRegisterAction.toDate}"></rich:calendar>
				</div>




				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<h:outputText value="District Name : "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					<h:outputText value="#{wholesaleStockRegisterAction.districtName}"
						rendered="#{wholesaleStockRegisterAction.districtFlag eq false}">
					</h:outputText>
					<h:selectOneMenu
						rendered="#{wholesaleStockRegisterAction.districtFlag eq true}"
						value="#{wholesaleStockRegisterAction.districtId}"
						onchange="this.form.submit();"
						style="height: 28px; width : 120px;">
						<f:selectItems
							value="#{wholesaleStockRegisterAction.districtList}" />
					</h:selectOneMenu>
				</div>
				<rich:spacer height="30px"></rich:spacer>
				<div align="center">
					<h:outputText value="WholeSale: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
						
						<h:outputText value="#{wholesaleStockRegisterAction.wholesaleName}"
						rendered="#{wholesaleStockRegisterAction.wsFlag eq false}">
					</h:outputText>

					<h:selectOneMenu
						value="#{wholesaleStockRegisterAction.wholesaleId}"
						style="height: 28px; width : 300px;"
						rendered="#{wholesaleStockRegisterAction.wsFlag eq true}">
						<f:selectItems
							value="#{wholesaleStockRegisterAction.wholesaleList}" />
					</h:selectOneMenu>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<br /> <br />
				<div class="row" align="center">

					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{wholesaleStockRegisterAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{wholesaleStockRegisterAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{wholesaleStockRegisterAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{wholesaleStockRegisterAction.reset}">
					</h:commandButton>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>