<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<h:form>
		<f:loadBundle basename="com.mentor.nl.userMessage" var="msgBundle" />
		<f:view>
			<div class="form-group">
				<div class="row " style="height: 30px"></div>
				<div class="col-md-12" align="left">
					<h:messages errorStyle="color:red"
						style="FONT-SIZE: x-large; FONT-WEIGHT: bold; background-color:"
						id="messages" infoStyle="color:green">
					</h:messages>
				</div>
				<div class="row " align="center">
					<h2>Distillery Production DataSheet(For 2019-20)</h2>
				</div>
				<div>
					<rich:separator lineType="dashed"></rich:separator>
				</div>
			</div>
			<div>
				<div class="row" align="center">
					<div class="col-md-4"></div>
					<div class="col-md-4"></div>
					<div class="col-md-4"></div>

				</div>
				<rich:spacer height="10px" />
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<div style="border-style: double; border-radius: 10px;">
							<rich:spacer height="10px" />
							<div class="row" align="center">
								<h:selectOneMenu onchange="this.form.submit();"
									valueChangeListener="#{distilleryProductionDataSheetAction.distChangeLisnr }"
									value="#{distilleryProductionDataSheetAction.distilleryId }"
									style="max-width: 300px;">
									<f:selectItems
										value="#{distilleryProductionDataSheetAction.distilleryList}" />
								</h:selectOneMenu>
							</div>
							<rich:spacer height="15px" />
							<div class="row">
								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Molasses Consumed:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.molasses_consumed_reserved }"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />
							<h:panelGroup rendered="false">
								<div class="row">

									<div class="col-md-5" align="right">
										<b style="COLOR: #000080;">Spirit Consumed: </b>
									</div>
									<div class="col-md-7" align="left">
										<div class="row">
											<div class="col-sm-4">a) Spirit :</div>
											<div class="col-sm-5" align="right">
												<h:outputText
													value="#{distilleryProductionDataSheetAction.spirit_consumed_spirit }"></h:outputText>
											</div>


										</div>
										<div class="row">
											<div class="col-sm-4">b) Denatured Spirit :</div>
											<div class="col-sm-5" align="right">
												<h:outputText
													value="#{distilleryProductionDataSheetAction.spirit_consumed_denatured_spirit }"></h:outputText>
											</div>
										</div>
										<div class="row"
											style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
											<div class="col-sm-4">
												<b>Total : </b>
											</div>
											<div class="col-sm-5" align="right">
												<h:outputText
													value="#{distilleryProductionDataSheetAction.spirit_consumed_denatured_spirit+distilleryProductionDataSheetAction.spirit_consumed_spirit }"
													style="FONT-WEIGHT: bold;"></h:outputText>
											</div>
										</div>
									</div>

								</div>
							</h:panelGroup>
							<rich:spacer height="15px" />
							<div class="row">
								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Produced by Molasses:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_produced_by_molasses }"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Produced by Grains:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_produced_by_grains }"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="10px" />

							<div class="row">
								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Purchased: </b>
								</div>
								<div class="col-md-7" align="left" style="FONT-WEIGHT: bold;">
									<div class="row">
										<div class="col-sm-4">a) UP :</div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_purchased_up }"></h:outputText>
										</div>


									</div>
									<div class="row" >
										<div class="col-sm-4">b)Export:</div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_purchased_export }"></h:outputText>
										</div>
									</div>


								</div>

							</div>
							<rich:spacer height="10px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b>Total Spirit Produced:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_produced_by_molasses+distilleryProductionDataSheetAction.spirit_produced_by_grains+distilleryProductionDataSheetAction.spirit_purchased_up+distilleryProductionDataSheetAction.spirit_purchased_export}"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Used for CL Production:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_used_cl_production }"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Used for FL Production:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_used_fl_production}"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="10px" />
							<div class="row">
								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Spirit Sold: </b>
								</div>
								<div class="col-md-7" align="left" style="FONT-WEIGHT: bold;">
									<div class="row">
										<div class="col-sm-4">a) UP :</div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_sold_up }"></h:outputText>
										</div>


									</div>
									<div class="row">
										<div class="col-sm-4">b)Export:</div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_sold_export }"></h:outputText>
										</div>
									</div>

								</div>

							</div>
							<rich:spacer height="10px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b >Total Spirit Used:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.spirit_used_cl_production+distilleryProductionDataSheetAction.spirit_used_fl_production+distilleryProductionDataSheetAction.spirit_sold_up+distilleryProductionDataSheetAction.spirit_sold_export}"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />


							<rich:spacer height="15px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Produced BL for CL (36 Degree):</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.produced_bl_for_cl}"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="15px" />
							<div class="row">

								<div class="col-md-5" align="right">
									<b style="COLOR: #000080;">Produced BL for FL:</b>
								</div>
								<div class="col-md-7" align="right">

									<div class="row"
										style="border-top: 1px solid black; border-bottom: 1px solid black; background: #bdbdbd">
										<div class="col-sm-4"></div>
										<div class="col-sm-5" align="right">
											<h:outputText
												value="#{distilleryProductionDataSheetAction.produced_bl_for_fl}"
												style="FONT-WEIGHT: bold;"></h:outputText>
										</div>
									</div>
								</div>

							</div>
							<rich:spacer height="10px" />
						</div>
					</div>
					<div class="col-md-3"></div>
				</div>


				<rich:spacer height="20px" />
			</div>
		</f:view>
	</h:form>
</ui:composition>