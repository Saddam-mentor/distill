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
							<h:outputText value="Oil Depot FL-41 Permit Search"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>
<div align="center">
				<h:outputLink styleClass="outputLinkEx"
						value="/"
						target="_self">
						<h:outputText styleClass="outputText" value="BACK "
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				</div>
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				
				
				<h:panelGroup rendered="#{oil_Depote_FL41_Permit_DetaileAction.panal_flag eq 'false' }">
				<div align="center"><h:outputText value="Indent No."/></div>
				<div align="center"><h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.orderNo}" style=" height : 22px; width: 200px"></h:inputText></div>
				<div align="center"><h:outputText value="Indent Date"/></div>
				<div align="center"><rich:calendar  value="#{oil_Depote_FL41_Permit_DetaileAction.orderDate }"/></div>
				<rich:spacer height="20px"/>
				<div class="row" align="center">
				<div class="col-sm-12">
						<div class="col-sm-5"></div>
							<div class="col-sm-2">
							<a4j:outputPanel id="cpp">
								<h:inputText disabled="true" styleClass="form-control"
									value="#{oil_Depote_FL41_Permit_DetaileAction.generateCaptcha1}"
									style="height:30px; font-size:20px; 
									color:blue;font-weight: bold;text-align: center;width:150px">
								</h:inputText>
							</a4j:outputPanel>
							</div>
							<div class="col-sm-5"></div>
						</div>
							<a4j:commandButton
								action="#{oil_Depote_FL41_Permit_DetaileAction.resetCptcha1}"
								image="/icons/refresh.png" style="width:60px; height:40px;">
								<a4j:support event="oncomplete" reRender="cpp"></a4j:support>
							</a4j:commandButton>
					
						</div>
						<div class="row" align="center">
							<i style="font-weight: bold; color: #003cb3; font-size: 14px;">Type
								the above code in box Given Below.</i>
						</div>
						
						<div class="row">
						<div class="col-sm-12">
						<div class="col-sm-5"></div>
							<div class="col-sm-2">
							<h:inputText styleClass="form-control" style="width:200px"    
							value="#{oil_Depote_FL41_Permit_DetaileAction.enterCaptcha1}">

							</h:inputText>
							</div>
							<div class="col-sm-5"></div>
						</div>
						</div>
						
				<div align="center"><h:commandButton value="Search" 
				action="#{oil_Depote_FL41_Permit_DetaileAction.search}"></h:commandButton>
				<rich:spacer height="20px"/>
				</div>
				
				</h:panelGroup>		
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				
 				<h:panelGroup rendered="#{oil_Depote_FL41_Permit_DetaileAction.panal_flag }">
				<div class="row"  align="center">
				<div class="col-md-2" align="right">
				<h:outputText value="Indent No. : " />
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.orderNo }"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Indent Date : "/>
				</div><div class="col-md-4" align="left">
				<rich:calendar  value="#{oil_Depote_FL41_Permit_DetaileAction.orderDate }" disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 
				 <div class="row"  align="center">
				 <div class="col-md-2" align="right">
				 <h:outputText value="Oil Depot Name : "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText   value="#{oil_Depote_FL41_Permit_DetaileAction.oil_dep_name}"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Oil Depot District : "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.district}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 <div class="row"  align="center">
				 <div class="col-md-2" align="right">
				 <h:outputText value="ENA Type : "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.ena_type }"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Distillery Name: "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText   value="#{oil_Depote_FL41_Permit_DetaileAction.ditillery_name}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 <div class="row"  align="center">
				 <div  class="col-md-2" align="right">
				 <h:outputText value="Approved Quantity: "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.approve_qty }"  disabled="true"/>
				</div><div class="col-md-2" align="right">
				<h:outputText value="Lifted Quantity: "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{oil_Depote_FL41_Permit_DetaileAction.lifted_qty}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 
				 
				 <rich:spacer height="10px"/>
				 
				</h:panelGroup>	
				
	
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div align="center"><h:commandButton value="Close" 
				action="#{oil_Depote_FL41_Permit_DetaileAction.search}"></h:commandButton>
				<rich:spacer height="20px"/>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>