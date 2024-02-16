
import ItemList from '@/views/ItemList';
import ItemListManage from '@/views/ItemListManage';
import ItemCreate from '@/views/ItemCreate';

export const itemRoutes = [ 
    

    {
        path: '/items',  
        name: 'ItemList', 
        component: ItemList 
    },
    {
        path: '/items/manage',
        name: 'ItemListManage',
        component: ItemListManage
    },
    {
        path: '/item/create',
        name: 'ItemCreate',
        component: ItemCreate
    }
]