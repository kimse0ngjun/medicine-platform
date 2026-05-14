import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../style/MyPage.css";

export default function MyPage() {
  const [user, setUser] = useState(null);
  const [remainTime, setRemainTime] = useState("");
  const [verifications, setVerifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = localStorage.getItem("user");

    if (!stored) return;

    const parsed = JSON.parse(stored);

    setUser(parsed);
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      const storedUser = localStorage.getItem("user");

      if (!storedUser) {
        setRemainTime("");
        return;
      }

      const user = JSON.parse(storedUser);

      const diff = user.expiresAt - Date.now();

      if (diff <= 0) {
        localStorage.removeItem("user");
        window.location.reload();
        return;
      }

      const minutes = Math.floor(diff / 1000 / 60);
      const seconds = Math.floor((diff / 1000) % 60);

      setRemainTime(
        `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`,
      );
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);

        const res = await fetch(
          `${import.meta.env.VITE_API_BASE_URL}/api/v1/verifications`,
        );

        const data = await res.json();

        setVerifications(data);
      } catch (e) {
        console.error(e);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("user");
    window.location.reload();
  };

  const statusColor = (status) => {
    switch (status) {
      case "SUCCESS":
        return "green";
      case "PROCESSING":
        return "orange";
      case "PENDING":
        return "gray";
      case "FAIL":
        return "red";
      default:
        return "gray";
    }
  };

  return (
    <div className="mypage-container">
      <header className="mypage-header">
        <Link to="/" className="logo">
          MedicinePlatform
        </Link>

        <div className="user-box">
          <div>
            <strong>{user?.nickname}</strong>
          </div>

          <div className="session">{remainTime}</div>

          <button onClick={handleLogout}>로그아웃</button>
        </div>
      </header>

      <main className="mypage-body">
        <section className="profile-card">
          <h2>내 정보</h2>

          <p>닉네임: {user?.nickname}</p>
          <p>이메일: {user?.email}</p>
          <p>세션: {remainTime}</p>
        </section>

        <section className="verification-section">
          <h2>검증 이력</h2>

          {loading && <p>로딩 중...</p>}

          {!loading && verifications.length === 0 && <p>이력이 없습니다.</p>}

          <div className="verification-list">
            {verifications.map((v) => (
              <div
                key={v.id}
                className="verification-card"
                style={{
                  borderLeft: `4px solid ${statusColor(v.status)}`,
                }}
              >
                <div className="top">
                  <span className="status">{v.status}</span>
                </div>

                <div className="content">
                  <p>
                    <b>입력값:</b> {v.inputText}
                  </p>
                  <p>
                    <b>LOT:</b> {v.lotNumber}
                  </p>
                  <p>
                    <b>결과:</b> {v.result}
                  </p>
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
}
