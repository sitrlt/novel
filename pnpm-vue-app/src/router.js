import {createRouter, createWebHashHistory} from 'vue-router'
import Index from "./admin/Index.vue"
import BookIndex from "./admin/qiantai/Index.vue"
import PublisherIndex from "./admin/Publisher/Index.vue"

const routes=[
    {path: '/link9',component: ()=>import("./components/test/test06.vue") },
    {path: '/t1',component: ()=>import("./components/test/test07.vue") },
    {path: '/link1',component: ()=>import("./components/test/test03.vue") },
    {path:'/t3',component:()=>import("./components/test/test02.vue")},
    {path:'/link7',component:()=>import('./components/test/test04.vue')},
    {path:'/link10',component:()=>import('./components/test/test08.vue')},
    {path:'/link11',component:()=>import('./components/takeaway/login.vue')},
    {path:'/link12',component:()=>import('./components/test/test09.vue')},
    {path:'/link13',component:()=>import('./components/test/test10.vue')},
    {path:'/link14',component:()=>import('./components/test/test11.vue')},
    {path:'/link15',component:()=>import('./components/test/test12.vue')},
    {path:'/link16',component:()=>import('./components/test/test13.vue')},
    {path:'/link20',component:()=>import('./components/test/test14.vue')},
    {path:'/link21',component:()=>import('./components/test/test15.vue')},
    {path:'/link22',component:()=>import('./components/test/test16.vue')},
    {path:'/link23',component:()=>import('./components/test/test17.vue')},
    {path:'/link24',component:()=>import('./components/test/test18.vue')},
    {path:'/link25',component:()=>import('./components/test/test19.vue')},
    {path:'/link26',component:()=>import('./components/test/test20.vue')},
    {path:'/',name:'login',component:()=>import('./admin/login/Login.vue')},
    {path:'/forgetPassword',component:()=>import('./admin/login/forgerPassword.vue')},
    {path:'/register',component:()=>import('./admin/login/Register.vue')},
    {path:'/link25',component:()=>import('./admin/qiantai/PersonalCenter.vue')},
    {path:'/new:id',name:'new',component:()=>import('./admin/qiantai/News1.vue')},
    {
        path:'/link8',
        component:()=>import('./components/test/test05.vue')
    },
    {
        path: '/novel',
        component:BookIndex,
        children: [
            {
                path:'/novel/shouye',
                name:'novel',
                component:()=>import('./admin/qiantai/shouye.vue')
            },
            {
                path:'/novel/desc/:id',
                name:'BookDesc',
                component:()=>import('./admin/qiantai/bookDesc.vue')
            },
            {
                path: '/novel/search-results',
                name:'BookSearch',
                component:()=>import('./admin/qiantai/BookSearch.vue')
            },
            {
                path: '/novel/ancient',
                name:'BookAncient',
                component:()=>import('./admin/qiantai/BookAncient.vue')
            },
            {
                path: '/novel/shelf/:id',
                name:'BookShelf',
                component:()=>import('./admin/qiantai/BookShelf.vue'),

            },
            {
                path: '/novel/modern',
                name:'BookModern',
                component:()=>import('./admin/qiantai/BookModern.vue')
            },
            {
                path: '/novel/desc/reviews/:id',
                name:'BookReviews',
                component:()=>import('./admin/qiantai/BookReviews.vue')
            },
            {
                path: '/novel/ranking',
                name: 'BookRanking',
                component:()=>import('./admin/qiantai/RankingList.vue')
            },
            {
                path: '/novel/history',
                name: 'BookHistory',
                component:()=>import('./admin/qiantai/BookHistory.vue')
            },
            {
                path: '/novel/society',
                name: 'BookSociety',
                component:()=>import('./admin/qiantai/BookSoceity.vue')
            }

        ]
    },
    {
        path: '/a1',
        name:'Index',
        component: Index,
        children: [
            {
                path: '/a1/l1',
                component: () => import('./admin/FirstPage.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l2',
                component: () => import('./admin/BookManagement/BookList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l4',
                component: () => import('./admin/goods/ReaderList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l5',
                component: () => import('./admin/LabelManagement/LabelList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l6',
                component: () => import('./admin/BorrowRecordManagement/PhysicalBorrowRecordList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l7',
                component: () => import('./admin/FeedbackManagement/FeedbackList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l8',
                component: () => import('./admin/BorrowRecordManagement/EbookBorrowRecordList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l9',
                component: () => import('./admin/ReservationManagement/ReservationList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l10',
                component: () => import('./admin/ActivityManagement/ActivityList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/a1/l11',
                component: () => import('./admin/login/ChangePassword.vue') // 这里根据实际的路径和组件名进行设置
            },
        ]

    },
    {
        path: '/publisher',
        name:'PublisherIndex',
        component: PublisherIndex,
        children: [
            {
                path: '/publisher/l1',
                component: () => import('./admin/Publisher/FirstPage.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path: '/publisher/l2',
                component: () => import('./admin/BookManagement/BookInsert.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path:'/publisher/l4',
                component: () => import('./admin/BookInventoryManagement/BookInventoryList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path:'/publisher/l3',
                component: () => import('./admin/BookManagement/BookToPublisherList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path:'/publisher/l5',
                component: () => import('./admin/FeedbackManagement/FeedbackToPublisherList.vue') // 这里根据实际的路径和组件名进行设置
            },
            {
                path:'/publisher/l6',
                component: () => import('./admin/login/ChangePasswordToPublisher.vue') // 这里根据实际的路径和组件名进行设置
            },
        ]
    }

]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router;