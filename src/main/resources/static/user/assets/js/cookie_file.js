// Hàm xử lý thanh toán
function processPayment(event) {
    event.preventDefault(); // Ngăn chặn gửi form

    // Gửi yêu cầu thanh toán đến server
    fetch('/users/payment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ /* Dữ liệu thanh toán */})
    })
        .then(response => {
            if (response.ok) {
                // Xóa dữ liệu đơn hàng khỏi cookie
                eraseCookie("cart");
                // Chuyển hướng đến trang xác nhận thanh toán
                window.location.href = '/payment-confirmation';
            } else {
                console.error('Payment failed');
                // Xử lý lỗi ở đây
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Xử lý lỗi ở đây
        });
}

// Gọi hàm gửi yêu cầu POST khi người dùng nhấn nút "Thanh toán"
document.getElementById('checkout-btn').addEventListener('click', sendCheckoutRequest);
