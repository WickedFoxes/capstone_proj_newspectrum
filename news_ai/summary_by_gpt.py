import os
import base64
from openai import OpenAI
from IPython.display import Image, display
import uuid
import re
from dotenv import load_dotenv

from model import *

load_dotenv()
API_KEY = os.getenv("OPENAI_API_KEY")
client = OpenAI(api_key=API_KEY)

def create_image_by_gpt(content, 
                        img_save_path="C:\\Users\\user\\project\\capstone_proj_newspectrum\\newspectrum\\src\\main\\resources\\static\\img"):
    prompt = f"""
    Create a 3-panel comic based on the following news article.

    - Do **not** include any text; use **only visuals** to tell the story.
    - Use a **cartoon/webtoon style**, and adjust the tone depending on the subject matter:
    - For general topics (e.g., politics, economy, culture): keep the style **light-hearted, fun, and creative**. You may incorporate **popular meme references**.
    - For sensitive topics (e.g., murder, suicide, abuse, violent crimes): use a more **serious, restrained tone**. Avoid humor or exaggeration and focus on conveying the **social impact** and **core message**.
    - The 3 panels should follow a **clear narrative structure**:
    1. **Introduction** – present the main character or context
    2. **Climax/Conflict** – show the core issue or event
    3. **Conclusion/Reaction** – depict the outcome, result, or social implication
    - Use **symbolic or intuitive visuals** to express people, settings, and key objects.
    - Make sure the story is **easily understandable** even for someone with **no prior knowledge** of the article.

    """
    prompt += content

    response = client.images.generate(
        model="gpt-image-1",
        prompt=prompt,
        size="1024x1024",
        n=1
    )
    image_bytes = base64.b64decode(response.data[0].b64_json)
    image_name = f"{uuid.uuid4()}.png"
    print(os.path.join(img_save_path, image_name))
    with open(os.path.join(img_save_path, image_name), "wb") as f:
        f.write(image_bytes)
    return image_name

def news_summary_by_gpt(content):
    prompt = f"""
    아래 뉴스 내용을 간결한 세 문장으로 요약해줘.
    - 모든 문장은 평서문으로 작성하라
    - 정보는 객관적으로, 사실 중심으로 요약해줘.
    - 사건의 핵심만을 20단어 이내로 간결하게 정리하라.
    - 날짜, 기자 이름은 생략하고, 사건의 본질에 집중해줘.

    """
    prompt += content
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": prompt},
        ]
    )
    summary = response.choices[0].message.content.strip()
    return summary



end_date_str = "2025-05-27 00:00:00"
start_date_str = "2025-05-25 00:00:00"
news_articles = read_news_articles_have_cluster(date_str=start_date_str,
                                                date_end=end_date_str)

print("aricle cnt:",len(news_articles))

for article in news_articles:
    title = str(article.title) if article.title else ""
    content = str(article.content) if article.content else ""

    # 텍스트 정리
    combined_text = (title + " " + content).replace('\n', ' ').replace('\r', ' ')
    cleaned_text = re.sub(r'\s+', ' ', combined_text).strip()

    # 비정상적인 문자 제거 (선택사항)
    cleaned_text = ''.join(char for char in cleaned_text if ord(char) < 65536)

    sumaary = news_summary_by_gpt(cleaned_text)
    article.summary = sumaary
    update_articles(article)


# print(len(articles_and_cluster))
# for cluster, articles in articles_and_cluster.items():
#     # print("cluster:", cluster)
#     for article in articles:
#         # print(article.title.replace('\n', ''))
#         comics_url = create_image_by_gpt('title : ' + article.title + '\n content : ' + article.summary)
#         article.comics_url = comics_url
#         update_articles(article)
        