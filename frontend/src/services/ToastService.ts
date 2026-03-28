import { toast } from 'react-toastify';

class ToastService {

  static INSTANCE = new ToastService();

  success(content: string, autoClose = 2000) {
    toast.success(content, {
      position: "bottom-right",
      theme: "colored",
      autoClose,
    });
  }

  error(content: string, autoClose = 2000) {
    toast.error(content, {
      position: "bottom-right",
      theme: "colored",
      autoClose,
    });
  }
}

export default ToastService.INSTANCE;

