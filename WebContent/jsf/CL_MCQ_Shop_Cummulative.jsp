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
							<h:outputText value=" CL Cummulative MGQ "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				 
				<div class="row col-md-12" align="center">
					<div class="col-md-6" align="right">
					<h:outputText value="Reporting Year  : "></h:outputText>
						
					</div>
					
						<div class="col-md-6" align="left"><h:selectOneMenu value="#{cL_MGQ_Cummulative_Action.bwfl_id}"
						styleClass="dropdown-menu" onchange="this.form.submit();"  
						 >	
						<f:selectItems value="#{lifitingComparisionFLAction.bwfl_list}" />
					</h:selectOneMenu></div>
				</div>
	<div class="col-md-12">
									<div align="center" style="font-size: 20px;">
										<h:selectOneRadio value="#{cL_MGQ_Cummulative_Action.radioDwCons}"
											valueChangeListener="#{cL_MGQ_Cummulative_Action.chngval}"
											onchange="this.form.submit();">
											<f:selectItem itemValue="D"
												itemLabel="Shop Wise" />
											<f:selectItem itemValue="C"
												itemLabel="Consolidated" />
										</h:selectOneRadio>
									</div>
								</div>
				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					
				</div>
					<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
<div class="row " align="center">
					<h:outputText  >
					 Month :</h:outputText>
					 
					 	<h:selectOneMenu
						
						value="#{cL_MGQ_Cummulative_Action.selectedMonth}"
						
						 valueChangeListener="#{cL_MGQ_Cummulative_Action.monthListener}"
						 onchange="this.form.submit();"
						style="height: 28px; width : 120px;" 	>
						
						<f:selectItems value="#{cL_MGQ_Cummulative_Action.monthList}" />
					</h:selectOneMenu>
				</div>
				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

					<div class="row col-md-12" align="center">
				<h:outputText  >
					 District :</h:outputText>
					 
					 	<h:selectOneMenu
						disabled="#{cL_MGQ_Cummulative_Action.radioDwCons eq 'C'}"
						value="#{cL_MGQ_Cummulative_Action.distid}"
						 valueChangeListener="#{cL_MGQ_Cummulative_Action.radioListener}"
						 onchange="this.form.submit();"
						style="height: 28px; width : 120px;" 	>
						<f:selectItems value="#{cL_MGQ_Cummulative_Action.districtList}" />
					</h:selectOneMenu>
				
					<h:outputText value="Sector/Circle*"
						styleClass="generalExciseStyle" ></h:outputText>
						
						
					<h:selectOneMenu 
						style="width: 250px; height: 28px;"
					disabled="#{cL_MGQ_Cummulative_Action.radioDwCons eq 'C'}"
						value="#{cL_MGQ_Cummulative_Action.sectorId}" 
						 valueChangeListener="#{cL_MGQ_Cummulative_Action.radioListenerSector}"
						 onchange="this.form.submit();"
						>

						<f:selectItems value="#{cL_MGQ_Cummulative_Action.sectorList}" />
					</h:selectOneMenu>
					
					
					<h:outputText  >
					 Shop :</h:outputText>
					<h:selectOneMenu
						
						disabled="#{cL_MGQ_Cummulative_Action.radioDwCons eq 'C'}"
								value="#{cL_MGQ_Cummulative_Action.shopId}"
							style="width: 250px; height: 28px;"
							 onchange="this.form.submit();"
							
							>
							
							
							
							<f:selectItems
								
								
								
								value="#{cL_MGQ_Cummulative_Action.shopList}" />
						
						
						
						</h:selectOneMenu>
							
					
					</div>

				<div class="row " align="center">
					
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report"
				
					styleClass="btn btn-success"
						action="#{cL_MGQ_Cummulative_Action.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
					
						value="/doc/ExciseUp/MIS/pdf/#{cL_MGQ_Cummulative_Action.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{cL_MGQ_Cummulative_Action.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					
				</div>
				
				<div class="col-md-12" align="center">


						<h:commandButton 
							action="#{cL_MGQ_Cummulative_Action.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							 />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{cL_MGQ_Cummulative_Action.exlname}CL_MGQ_Achievement.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{cL_MGQ_Cummulative_Action.excelFlag==true}"
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
						action="#{cL_MGQ_Cummulative_Action.reset}">
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