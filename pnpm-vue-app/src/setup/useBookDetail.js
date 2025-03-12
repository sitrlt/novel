// useBookDetail.js
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

export function useBookDetail() {
    const route = useRoute();
    const currentBook = ref(null);
    const status = ref('待处理');

    const loadBookDetail = async (bookId) => {
        try {
            const response = await axios.get(`http://localhost:8080/book/findById/${bookId}`);
            currentBook.value = response.data;
            if (currentBook.value.ebook === true) {
                status.value = '借阅中';
            } else {
                status.value = '待处理';
            }
        } catch (error) {
            console.error('请求出错:', error);
        }
    };

    onMounted(() => {
        loadBookDetail(route.params.id);
    });

    watch(route.params, () => {
        loadBookDetail(route.params.id);
    }, { deep: true });

    return { currentBook, status, loadBookDetail };
}