<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
	
	<rich:spacer height="80"></rich:spacer>
	

	
	
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
							<h:outputText value=" Yearly CL MGQ Achievement"
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
					
							  valueChangeListener="#{cL2_Mgq_ReportAction.radioListener}"
								value="#{cL2_Mgq_ReportAction.radio}"
								onchange="this.form.submit();">
							<f:selectItem itemValue="S" itemLabel="Shopwise" />
								<rich:spacer width="100px"></rich:spacer>
							
							<f:selectItem itemValue="C" itemLabel="Consolidated" />
							

						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div align="center">
					<h:outputText value="Select Year: "
						style="FONT-SIZE:15px; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu
						value="#{cL2_Mgq_ReportAction.year}"
						style="height: 25px; width : 150px;">
						<f:selectItems
							value="#{cL2_Mgq_ReportAction.getAll_List}"/>
					</h:selectOneMenu>
				</div>
				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

					<div class="row col-md-12" align="center">
				<h:outputText  rendered="#{cL2_Mgq_ReportAction.radio eq 'S'}">
					 District :</h:outputText>	<h:selectOneMenu
						 rendered="#{cL2_Mgq_ReportAction.radio eq 'S'}"
						value="#{cL2_Mgq_ReportAction.distid}"
						 valueChangeListener="#{cL2_Mgq_ReportAction.radioListener}"
						style="height: 28px; width : 120px;" 	onchange="this.form.submit();">
						<f:selectItems value="#{stockAtWholeSellersAction.districtList}" />
					</h:selectOneMenu>
					<h:outputText  rendered="#{cL2_Mgq_ReportAction.radio eq 'S'}">
					 Shop :</h:outputText>
					<h:selectOneMenu
							 rendered="#{cL2_Mgq_ReportAction.radio eq 'S'}"
						
								value="#{cL2_Mgq_ReportAction.shopId}"
							style="width: 250px; height: 28px;">
							
							
							
							<f:selectItems
								
								
								
								value="#{cL2_Mgq_ReportAction.shopList}" />
						
						
						
						</h:selectOneMenu>
							
					
					</div>

				<div class="row " align="center">
					
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report"
				
					styleClass="btn btn-success"
						action="#{cL2_Mgq_ReportAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
					
						value="/doc/ExciseUp/MIS/pdf/#{cL2_Mgq_ReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{cL2_Mgq_ReportAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					
				</div>
				
				<div class="col-md-12" align="center">


						<h:commandButton 
							action="#{cL2_Mgq_ReportAction.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							 />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{cL2_Mgq_ReportAction.exlname}CL_MGQ_Achievement.xlsx"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{cL2_Mgq_ReportAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
				
				
				<div 
				class="row" align="center">
					

					

					
				</div>
				<br />
				<div class="col-md-12" align="center">
					
<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{cL2_Mgq_ReportAction.reset}">
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