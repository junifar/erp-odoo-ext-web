$(document).ready(function(){
    var Router = Backbone.Router.extend({
       routes: {
           "myroute" : "myFunc"
       },

        myFunc: function(myroute){
           document.write(myroute);
        }
    });

    var router = new Router();
    Backbone.history.start();

    var AppView = Backbone.View.extend({
       el: '#container',

       initialize: function(){
            this.render();
       },

       render: function(){
           this.$el.html("Halo Apa Kabar");
       }
    });

    var appView = new AppView();
});