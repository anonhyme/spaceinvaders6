##TODO
* make the GridDemoModule a widget
* add graph to widget


wait async call to complete and fire event 
 So instead of blocking the execution until your async code is finished do following:
 
 Create a Event which is fired on the global Eventbus when your async code is finished
 Attach a Handler for this event in one of your Presenters
 Start the async code
 Show a loading indicator
 When the async call is finished hide the loading indicator and fire the Event on the Eventbus
 Handle the next step in the Handler you created before.