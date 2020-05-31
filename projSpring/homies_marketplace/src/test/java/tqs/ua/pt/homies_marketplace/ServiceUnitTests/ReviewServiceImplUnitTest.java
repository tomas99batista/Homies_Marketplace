package tqs.ua.pt.homies_marketplace.ServiceUnitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.repository.ReviewRepository;
import tqs.ua.pt.homies_marketplace.service.ReviewServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplUnitTest {

    @Mock(lenient=true)
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void whenPostReview_thenReviewShouldBeSaved() {
        Review review= new Review("jose@email.com", 2.5, "comment1");
        Mockito.when(reviewRepository.save(review)).thenReturn(review);
        Review saved = reviewService.save(review);
        assertThat(saved.getComment()).isEqualTo(review.getComment());
    }


    @Test
    void whenFindReview_IfReviewExists_thenReturnReview(){
        Review review= new Review("jose@email.com", 2.5, "comment1");
        List<Review> allReviews=new ArrayList<>();
        allReviews.add(review);
        Mockito.when(reviewRepository.findReviews(1L)).thenReturn(allReviews);
        List<Review> fromDb=reviewService.getReviews(1L);
        assertThat(fromDb).hasSize(1).extracting(Review::getRating).contains(2.5);

    }
}
