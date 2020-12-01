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
							<h:outputText value="ENA Order Search"
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
				
				
				<h:panelGroup rendered="#{eNA_Permit_SearchAction.panal_flag eq 'false' }">
				<div align="center"><h:outputText value="ENA Order No."/></div>
				<div align="center"><h:inputText  value="#{eNA_Permit_SearchAction.orderNo}" style=" height : 22px; width: 200px"></h:inputText></div>
				<div align="center"><h:outputText value="ENA Order Date"/></div>
				<div align="center"><rich:calendar  value="#{eNA_Permit_SearchAction.orderDate }"/></div>
				<rich:spacer height="20px"/>
				<div class="row" align="center">
				<div class="col-sm-12">
						<div class="col-sm-5"></div>
							<div class="col-sm-2">
							<a4j:outputPanel id="cpp">
								<h:inputText disabled="true" styleClass="form-control"
									value="#{eNA_Permit_SearchAction.generateCaptcha1}"
									style="height:30px; font-size:20px; 
									color:blue;font-weight: bold;text-align: center;width:150px">
								</h:inputText>
							</a4j:outputPanel>
							</div>
							<div class="col-sm-5"></div>
						</div>
							<a4j:commandButton
								action="#{eNA_Permit_SearchAction.resetCptcha1}"
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
							value="#{eNA_Permit_SearchAction.enterCaptcha1}">

							</h:inputText>
							</div>
							<div class="col-sm-5"></div>
						</div>
						</div>
						
				<div align="center"><h:commandButton value="Search" 
				action="#{eNA_Permit_SearchAction.search}"></h:commandButton>
				 
				</div>
				
				</h:panelGroup>		
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				
 				<h:panelGroup rendered="#{eNA_Permit_SearchAction.panal_flag }">
				<div class="row"  align="center">
				<div class="col-md-2" align="right">
				<h:outputText value="Order No. : " />
				</div><div class="col-md-4" align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.orderNo }"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Order Date : "/>
				</div><div class="col-md-4" align="left">
				<rich:calendar  value="#{eNA_Permit_SearchAction.orderDate }" disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 
				 <div class="row"  align="center">
				 <div class="col-md-2" align="right">
				 <h:outputText value="Purchaser Distillery : "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.pur_dist}"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Seller Distillery : "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.seller_dist}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 <div class="row"  align="center">
				 <div class="col-md-2" align="right">
				 <h:outputText value="ENA Type Requested: "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.ena_type }"  disabled="true"/>
				</div>
				<div class="col-md-2" align="right">
				<h:outputText value="Purpuse Of ENA: "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.pur_ena}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 <div class="row"  align="center">
				 <div  class="col-md-2" align="right">
				 <h:outputText value="Request Date: "/>
				 </div><div class="col-md-4"  align="left">
				<rich:calendar  value="#{eNA_Permit_SearchAction.request_Date }"  disabled="true"/>
				</div><div class="col-md-2" align="right">
				<h:outputText value="Approved ENA(BL): "/>
				</div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.approved_ena}"  disabled="true"/>
				</div> </div>
				 
				 <rich:spacer height="10px"/>
				 <div class="row"  align="center">
				 <div class="col-md-2" align="right">
				 <h:outputText value="ENA Lifted(BL) : "/>
				 </div><div class="col-md-4"  align="left">
				<h:inputText  value="#{eNA_Permit_SearchAction.lifted_ena}"  disabled="true"/>
				</div>
				
				<div class="col-md-2"><h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//Distillery//ENA_order//#{eNA_Permit_SearchAction.pdf}"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Order"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink></div>
				
				 </div>
				 
				 <rich:spacer height="10px"/>
				 
				</h:panelGroup>	
				
	
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div align="center"><h:commandButton value="Close" 
				action="#{eNA_Permit_SearchAction.back}"></h:commandButton>
				<rich:spacer height="20px"/>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>