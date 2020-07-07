import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from "./views/PipelineView";
import Settings from "./views/SettingsView";
Vue.use(Router);


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
        path: '/',
        redirect: '/dashboard'
    }, {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard
    }, {
        path: '/settings',
        name: 'Settings',
        component: Settings
    }]
})
